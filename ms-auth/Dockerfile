FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/ms-auth-*.jar app.jar
EXPOSE 8002
ENTRYPOINT ["java", "-jar", "app.jar"]