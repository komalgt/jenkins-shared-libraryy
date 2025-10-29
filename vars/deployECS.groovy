/**
 * Deploy to AWS ECS via shared library.
 * Usage:
 *   deployECS(
 *     cluster: 'my-cluster',
 *     service: 'my-service',
 *     image: 'myrepo/myimage:tag',
 *     awsRegion: 'us-east-1',
 *     awsCredentialsId: 'aws-creds'
 *   )
 */
def call(Map config = [:]) {
    def deployer = new org.example.ECSDeployer(this)
    deployer.deploy(
        cluster: config.cluster,
        service: config.service,
        image: config.image,
        awsRegion: config.awsRegion,
        awsCredentialsId: config.awsCredentialsId
    )
}
