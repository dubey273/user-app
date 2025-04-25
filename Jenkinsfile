def dockerImage
pipeline {
    agent any
    environment {
        GRADLE_OPTS = "-Dorg.gradle.jvmargs='-Xmx1024m'"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/dubey273/user-app'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('Package') {
            steps {
                sh './gradlew bootJar'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                   dockerImage = docker.build("rdubey273/user-app:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: '78a67b33-ee0d-41f0-911d-6a292eea3ac7', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        echo "Logging in to Docker Hub as $DOCKER_USER"
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                        dockerImage.push()
                    }
                }
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
