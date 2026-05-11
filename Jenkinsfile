node {
    def project_name = "upload-service"
    def image_version = "1.0.0"
    def finalImage = "${project_name}:${image_version}"

    try {
        notifyBuild('STARTED')

        stage('Clone repository') {
            checkout scm
        }

        stage('Build Docker Image (multi-stage)') {
            echo "Building final Docker image with multi-stage Dockerfile..."
            app = docker.build(finalImage, "-f Dockerfile .")
        }

        stage('Deploy Container') {
            echo "Deploying container..."

            sh "docker stop ${project_name} || true"
            sh "docker rm ${project_name} || true"

            sh """
                docker run -d \
                    --name ${project_name} \
                    --network all_database_app \
                    -p 8899:8899 \
                    ${finalImage}
            """
        }

    } catch (e) {
        currentBuild.result = "FAILED"
        throw e
    } finally {
        notifyBuild(currentBuild.result)
    }
}

def notifyBuild(String buildStatus = 'STARTED') {
    buildStatus = buildStatus ?: 'SUCCESSFUL'

    echo "Build status: ${buildStatus}"
}
