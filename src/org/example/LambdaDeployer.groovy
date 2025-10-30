#LambdaDeployer.groovy
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
        steps.withCredentials([steps.usernamePassword(
            credentialsId: config.awsCredentialsId,
            usernameVariable: 'AWS_ACCESS_KEY_ID',
            passwordVariable: 'AWS_SECRET_ACCESS_KEY'
        )]) {
            steps.sh """
                export AWS_ACCESS_KEY_ID=\$AWS_ACCESS_KEY_ID
                export AWS_SECRET_ACCESS_KEY=\$AWS_SECRET_ACCESS_KEY
                export AWS_DEFAULT_REGION=${config.awsRegion}

                aws lambda update-function-code \\
                  --function-name ${config.functionName} \\
                  --s3-bucket ${config.s3Bucket} \\
                  --s3-key ${config.s3Key}
            """
        }
        steps.echo "Deployment to Lambda function '${config.functionName}' in region '${config.awsRegion}' initiated."
    }
}
