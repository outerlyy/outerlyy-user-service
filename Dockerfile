
# # Prepare runtime.
# FROM openjdk:8-jdk-alpine
# # RUN addgroup -S spring && adduser -S spring -G spring
# # USER spring:spring
# RUN ./gradlew bootJar
# VOLUME /tmp
# COPY target/*.jar app.jar
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","/app.jar"]