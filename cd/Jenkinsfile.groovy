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
                    } else if (params.REGION == null || params.REGION == "") {
                        error("Can't proceed without REGION")
                    } else if (params.CF_ROLE == null || params.CF_ROLE == "") {
                        error("Can't proceed without CloudFormation IAM role")
                    } else if (params.JENKINS_ROLE == null || params.JENKINS_ROLE == "") {
                        error("Can't proceed without Jenkins IAM role")
                    }
                }
            }
        }

        stage('Build') {
            steps {
                sh "mvn clean install -DuseCommitHash -DskipTests -T 1C"
            }
        }

        stage("Unit tests") {
            steps {
                sh "mvn test -Dmaven.test.failure.ignore=true -T 1C"
            }
        }

        stage('Set required environment variables') {
            steps {
                script {

                    env.VPC_PARAMS_PATH = "cd/cloudformation/vpc/${REGION}/vpc_params.yaml"
                    env.S3_PARAMS_PATH = "cd/cloudformation/s3/${REGION}/s3_params.yaml"
                    env.ELB_PARAMS_PATH = "cd/cloudformation/elb/${REGION}/elb_params.yaml"
                    env.ASG_PARAMS_PATH = "cd/cloudformation/asg/${REGION}/asg_params.yaml"

                    env.VPC_TEMPLATE_PATH = "cd/cloudformation/vpc/vpc_cfn.yaml"
                    env.S3_TEMPLATE_PATH = "cd/cloudformation/s3/s3_cfn.yaml"
                    env.ELB_TEMPLATE_PATH = "cd/cloudformation/elb/elb_cfn.yaml"
                    env.ASG_TEMPLATE_PATH = "cd/cloudformation/asg/asg_cfn.yaml"

                    env.VPC_STACK_NAME = "aws-gt-vpc"

                    env.AWS_PARTITION = "aws"
                    env.CF_ROLE = "arn:${AWS_PARTITION}:iam::${AWS_ACCOUNT}:role/${CF_ROLE}"
                }
            }
        }

        stage('Update VPC CloudFormation') {
            steps {
                script {
                    //noinspection GroovyAssignabilityCheck
                    withAWS(role: "${JENKINS_ROLE}", roleAccount: "${AWS_ACCOUNT}", region: "${REGION}") {
                        VPC_STACK_NAME = sh returnStdout: true, script: "aws cloudformation describe-stacks --output json | jq -r '.Stacks[].StackName' | grep \"$VPC_STACK_NAME\" | tr -d '\040\011\012\015'"
                        if (VPC_STACK_NAME == null || VPC_STACK_NAME == '') {
                            cfnValidate(file: "${VPC_TEMPLATE_PATH}")
                            cfnUpdate(stack: "${VPC_STACK_NAME}", file: "${VPC_TEMPLATE_PATH}", paramsFile: "${VPC_PARAMS_PATH}", roleArn: "${CF_ROLE}", tags: ["Name=aws-gt-vpc", "Project=AWSGT"])
                        }
                    }
                }
            }
        }


    }

    post {
        always {
            junit '**/target/surefire-reports/*.xml'
            junit '**/target/failsafe-reports/*.xml'
        }
        success {
            script {
                currentBuild.result = 'SUCCESS'
            }
        }
        failure {
            script {
                currentBuild.result = 'FAILURE'
            }
        }
        unstable {
            script {
                currentBuild.result = 'UNSTABLE'
            }
        }
    }
}