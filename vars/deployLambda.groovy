#deployLambda.groovy
/**
 * Deploy AWS Lambda function via shared library.
 * Usage:
 *   deployLambda(
 *     functionName: 'myFunction',
 *     artifactPath: 'build/function.zip',
 *     awsRegion: 'us-east-1',
 *     awsCredentialsId: 'aws-creds'
 *   )
 */
def call(Map config = [:]) {
    def deployer = new org.example.LambdaDeployer(this)
    deployer.deploy(
        functionName: config.functionName,
        artifactPath: config.artifactPath,
        awsRegion: config.awsRegion,
        awsCredentialsId: config.awsCredentialsId
    )
}
