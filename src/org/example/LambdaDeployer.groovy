package org.example

class LambdaDeployer implements Serializable {
    def steps

    LambdaDeployer(steps) {
        this.steps = steps
    }

    /**
     * Deploys code to an AWS Lambda function.
     * Usage params: functionName, s3Bucket, s3Key, awsRegion, awsCredentialsId
     */
    def deploy(Map config) {
        steps.echo "Deploy config: ${config.inspect()}" // for easier Groovy Map debug
        steps.withCredentials([[
            $class: 'AmazonWebServicesCredentialsBinding',
            credentialsId: config.awsCredentialsId
        ]]) {
            steps.sh """
                aws lambda update-function-code \\
                  --function-name '${config.functionName}' \\
                  --s3-bucket '${config.s3Bucket}' \\
                  --s3-key '${config.s3Key}' \\
                  --region '${config.awsRegion}'
            """
        }
        steps.echo "Deployment to Lambda '${config.functionName}' in region '${config.awsRegion}' complete."
    }
}
