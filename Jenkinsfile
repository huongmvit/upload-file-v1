node {

    // ===== CONFIG =====
    def PROJECT_NAME = "upload-service"
    def PORT = "9996"
    def IMAGE_VERSION = "1.0.0"
    def NETWORK = "all_database_app"

    script {

        // ===== NOTIFY (Closure – an toàn CPS) =====
        def notifyBuild = { status ->
            echo "===== BUILD STATUS: ${status} ====="
        }

        try {
            notifyBuild("STARTED")

            stage('Checkout') {
                checkout scm
            }

            stage('Build Docker Image') {
                configFileProvider([
                    configFile(fileId: 'maven-settings-cha', targetLocation: 'settings.xml')
                ]) {
                    sh "docker build -t ${PROJECT_NAME}:${IMAGE_VERSION} ."
                }
            }

            stage('Deploy') {
                sh """
                    docker stop ${PROJECT_NAME} || true
                    docker rm ${PROJECT_NAME} || true

                    mkdir -p /data/uploads/${PROJECT_NAME}
                    mkdir -p /var/logs/${PROJECT_NAME}

                    docker run -d \
                      --restart unless-stopped \
                      --name ${PROJECT_NAME} \
                      --network ${NETWORK} \
                      -p ${PORT}:${PORT} \
                      -v /var/logs/${PROJECT_NAME}:/var/logs/${PROJECT_NAME} \
                      -v /data/uploads/${PROJECT_NAME}:/app/uploads \
                      -e SPRING_PROFILES_ACTIVE=prod \
                      -e TZ=Asia/Ho_Chi_Minh \
                      ${PROJECT_NAME}:${IMAGE_VERSION}
                """
            }

            notifyBuild("SUCCESS")

        } catch (err) {
            currentBuild.result = "FAILED"
            notifyBuild("FAILED")
            throw err
        }
    }
}
