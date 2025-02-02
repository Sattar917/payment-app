FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/eureka-server-*.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "app.jar"]