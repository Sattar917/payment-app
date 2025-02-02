FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/payment-gateway-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]