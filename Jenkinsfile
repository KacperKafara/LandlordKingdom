pipeline {
    agent any

    stages {
        stage('Build java') {
            steps {
                sh 'mvn clean package'
            }
        }
        stage('Build js') {
            steps {
                dir('./webapp/ssbd202402') {
                    sh 'yarn build'
                }
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}