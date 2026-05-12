# ===== BUILD STAGE =====
FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /build

RUN apk add --no-cache maven

# 👉 copy settings.xml vào đúng chỗ Maven dùng
COPY settings.xml /root/.m2/settings.xml

# copy pom trước để cache layer
COPY pom.xml .
RUN mvn -B -DskipTests dependency:go-offline

# copy source sau
COPY src ./src
RUN mvn -B -DskipTests package


# ===== RUNTIME STAGE =====
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ENV TZ=Asia/Ho_Chi_Minh
COPY --from=build /build/target/*.jar app.jar

EXPOSE 9996
ENTRYPOINT ["java","-jar","app.jar"]
