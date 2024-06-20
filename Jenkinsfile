pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "task-management-api:latest"
        REGISTRY_CREDENTIALS = 'dockerhub-credentials-id'
        REGISTRY_URL = 'your-docker-registry-url'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    // Compile the project using Maven
                    sh 'mvn clean package'
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    // Run unit tests
                    sh 'mvn test'
                }
            }
        }

        stage('Code Coverage') {
            steps {
                script {
                    // Run code coverage analysis
                    sh 'mvn jacoco:report'
                }
            }
            post {
                always {
                    // Publish code coverage results
                    jacoco execPattern: 'target/jacoco.exec', classPattern: 'target/classes', sourcePattern: 'src/main/java', inclusionPattern: '**/*.class'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry("https://${REGISTRY_URL}", REGISTRY_CREDENTIALS) {
                        sh 'docker push ${DOCKER_IMAGE}'
                    }
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Deploy the application (this is just an example, adjust to your needs)
                    sh 'docker run -d -p 8080:8080 ${DOCKER_IMAGE}'
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            // Notify success
            echo 'Build and deployment completed successfully!'
        }
        failure {
            // Notify failure
            echo 'Build or deployment failed.'
        }
    }
}