pipeline {
    agent any
    tools {
        nodejs 'nodejs'
    }

    stages {
        stage('Build java') {
            steps {
                sh 'mvn -B -Dmaven.test.skip clean package '
            }
        }
        stage('Build js') {
            steps {
                dir('./webapp/ssbd202402') {
                    sh 'yarn'
                    sh 'yarn build'
                }
            }
        }
        stage('Unit tests') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }
    }
}
