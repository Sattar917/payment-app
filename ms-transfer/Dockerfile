FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/ms-transfer-*.jar app.jar
EXPOSE 8001
ENTRYPOINT ["java", "-jar", "app.jar"]