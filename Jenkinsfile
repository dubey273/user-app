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

        stage('SonarQube Analysis') {
                    steps {
                        script {
                            try {
                                withSonarQubeEnv(credentialsId: 'sonar-qube', installationName: 'SonarQube') {
                                    sh '''
                                        echo "Running sonar-scanner in $(pwd)"
                                        sonar-scanner --debug
                                    '''
                                }
                            } catch (Exception e) {
                                echo "SonarQube analysis failed: ${e.getMessage()}"
                                error "Pipeline aborted due to SonarQube analysis failure: ${e.getMessage()}"
                            }
                        }
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

         stage('Push Docker Image Test') {
                   steps {
                   script {
                    try {
                   docker.withRegistry('https://index.docker.io/v1/', 'docker-hub-id') {
                               dockerImage.push()
                           }
                           }
                           catch (Exception e) {
                                                              echo "Push failed: ${e.getMessage()}"
                                                              error "Push Docker Image stage failed"
                                                          }

                       }

                   }
         }
/*
        stage('Push Docker Image') {
           steps {
                           echo "Entering Push Docker Image stage"
                           sh 'env | grep -i registry || echo "No registry env vars found"'
                           script {
                               try {
                                   withCredentials([usernamePassword(credentialsId: 'docker-hub-id', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                                       echo "Inside withCredentials: Username is $DOCKER_USER"
                                       sh "echo \$DOCKER_PASS | docker login index.docker.io -u \$DOCKER_USER --password-stdin"
                                       echo "Pushing Docker image ${DOCKERHUB_REGISTRY}:${env.BUILD_NUMBER} to Docker Hub"
                                       dockerImage.push()
                                   }
                               } catch (Exception e) {
                                   echo "Push failed: ${e.getMessage()}"
                                   error "Push Docker Image stage failed"
                               }
                           }
                       }
        }
*/
}
    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
