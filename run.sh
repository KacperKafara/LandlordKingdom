#!/bin/bash

minikube config set memory 8192
minikube config set cpus 6

minikube start --extra-config=apiserver.service-node-port-range=1-65535
minikube addons enable metrics-server

helm repo add kube-logging https://kube-logging.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami

helm install --create-namespace --namespace logging logging-operator oci://ghcr.io/kube-logging/helm-charts/logging-operator --version 4.11.2-dev.1
helm install --create-namespace --namespace logging elasticsearch bitnami/elasticsearch --version 21.3.26 --set global.kibanaEnabled=true --set coordinating.heapSize=378m
helm install --create-namespace --namespace logging logging-operator-logging kube-logging/logging-operator-logging --version 4.2.2 -f ./values/logging-operator-logging-elastic-conf.yaml
helm install --create-namespace --namespace monitoring monitoring ./monitoring --set prometheus.prometheusSpec.serviceMonitorSelectorNilUsersHelmValues=false -f ./values/prometheus-conf.yaml

helm install landlord ./helm

echo "Waiting for all pods to be in 'Running' state..."
sleep 15
while [[ $(kubectl get pods -A --no-headers | grep -E 'monitoring|landlord|logging' | grep -Ev 'Running|Completed' | wc -l) -ne 0 ]]; do
    echo "Not all pods are in 'Running' state. Waiting 5 seconds..."
    sleep 5
done

minikube service fe-service -n landlord &
minikube service kibana-elasticsearch -n logging &
minikube service monitoring-grafana -n monitoring &