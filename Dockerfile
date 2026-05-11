# =============================
# Stage 1: Build JAR
# =============================
FROM eclipse-temurin:21-jdk-alpine AS builder

RUN apk add --no-cache maven
WORKDIR /build

# Copy custom settings
COPY settings.xml /root/.m2/settings.xml

COPY . .

RUN mvn clean package -Dmaven.test.skip=true

# =============================
# Stage 2: Runtime
# =============================
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

COPY --from=builder /build/target/*.jar app.jar

ENV TZ="Asia/Ho_Chi_Minh"
ENV LOG_PATH="/app/logs"
ENV SERVICE_NAME="iam-service"

RUN mkdir -p /app/logs

EXPOSE 8899

ENTRYPOINT ["java", "-jar", "app.jar"]
