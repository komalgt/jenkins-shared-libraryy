package org.example

class ECSDeployer implements Serializable {
    def steps
    ECSDeployer(steps) { this.steps = steps }
    def deploy(Map config) {
        steps.withCredentials([steps.usernamePassword(
            credentialsId: config.awsCredentialsId,
            usernameVariable: 'AWS_ACCESS_KEY_ID',
            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
        )]) {
            steps.sh """
                export AWS_ACCESS_KEY_ID=\$AWS_ACCESS_KEY_ID
                export AWS_SECRET_ACCESS_KEY=\$AWS_SECRET_ACCESS_KEY
                aws ecs update-service \
                    --cluster ${config.cluster} \
                    --service ${config.service} \
                    --force-new-deployment \
                    --region ${config.awsRegion}
            """
        }
        steps.echo "Successfully deployed ECS service: ${config.service}"
    }
}
