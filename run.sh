#!/bin/bash

minikube start --extra-config=apiserver.service-node-port-range=1-65535

kubectl apply -k k8s

echo "Waiting for all pods to be in 'Running' state..."
sleep 15
while [[ $(kubectl get pods -n landlord --no-headers | grep -v 'Running' | wc -l) -ne 0 ]]; do
    echo "Not all pods are in 'Running' state. Waiting 5 seconds..."
    sleep 5
done

minikube service fe-service -n landlord
