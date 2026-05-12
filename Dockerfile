# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk-alpine AS build

WORKDIR /build

RUN apk add --no-cache maven

# Maven settings
COPY settings.xml /root/.m2/settings.xml

# Cache dependency
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# Source
COPY src ./src

RUN mvn -B -DskipTests package


# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

ENV TZ=Asia/Ho_Chi_Minh

# 🔥 tạo thư mục upload
RUN mkdir -p /app/uploads

# copy jar
COPY --from=build /build/target/*.jar app.jar

# expose port
EXPOSE 9996

# 🔥 khai báo volume
VOLUME ["/app/uploads"]

ENTRYPOINT ["java","-jar","app.jar"]