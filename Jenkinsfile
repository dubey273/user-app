pipeline {

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
                    dockerImage = docker.build("your-dockerhub-username/user-app:${env.BUILD_NUMBER}")
                }
            }
        }
/*
        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: '87c161d9-85f0-43ee-a077-345a05efa48f', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    script {
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin"
                        dockerImage.push()
                    }
                }
            }
        } */
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
