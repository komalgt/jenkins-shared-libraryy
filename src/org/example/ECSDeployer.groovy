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
            steps.sh """
                export AWS_DEFAULT_REGION=${config.awsRegion}
                aws ecs update-service \\
                    --cluster ${config.cluster} \\
                    --service ${config.service} \\
                    --force-new-deployment \\
                    --region ${config.awsRegion}
            """
        }
        steps.echo "Successfully deployed ECS service: ${config.service}"
    }
}
