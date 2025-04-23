pipeline {
    agent any

    tools {
        // Make sure this matches the JDK installed in Jenkins
        jdk 'jdk-23.0.2'
    }

    environment {
        GRADLE_OPTS = "-Dorg.gradle.jvmargs='-Xmx1024m'"
    }

    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/your-username/your-gradle-project.git'
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
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
