#!groovy

//noinspection GroovyAssignabilityCheck
pipeline {

    agent any

    stages {
        stage('Clean working directory and Checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage('Validate parameters') {
            steps {
                script {
                    if (params.AWS_ACCOUNT == null || params.AWS_ACCOUNT == "") {
                        error("Can't proceed without AWS Account Id")
                    }
                }
            }
        }

    }
}