package org.example

class ECSDeployer implements Serializable {
    def steps

    ECSDeployer(steps) { this.steps = steps }

    /**
     * Deploys to an AWS ECS service.
     * Usage params: cluster, service, awsRegion, awsCredentialsId
     */
    def deploy(Map config) {
        steps.withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: config.awsCredentialsId
        ]]) {
            steps.sh '''
                export AWS_DEFAULT_REGION='${config.awsRegion
