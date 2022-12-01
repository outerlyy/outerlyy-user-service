FROM gradle:7.0.2-jdk8 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
# Download proto zip
# ENV PROTOC_ZIP=protoc-3.14.0-linux-x86_64.zip
# RUN curl -OL https://github.com/protocolbuffers/protobuf/releases/download/v3.14.0/${PROTOC_ZIP}
# RUN unzip -o ${PROTOC_ZIP} -d ./proto 
# RUN chmod 755 -R ./proto/bin
# ENV BASE=/usr
# # Copy into path
# RUN cp ./proto/bin/protoc ${BASE}/bin/
# RUN cp -R ./proto/include/* ${BASE}/include/

# # Download protoc-gen-grpc-web
# ENV GRPC_WEB=protoc-gen-grpc-web-1.2.1-linux-x86_64
# ENV GRPC_WEB_PATH=/usr/bin/protoc-gen-grpc-web
# RUN curl -OL https://github.com/grpc/grpc-web/releases/download/1.2.1/${GRPC_WEB}

# # Copy into path
# RUN mv ${GRPC_WEB} ${GRPC_WEB_PATH}
# RUN chmod +x ${GRPC_WEB_PATH}
# # RUN gradle build --no-daemon

RUN gradle build -i --stacktrace

FROM openjdk:8-jre-slim

EXPOSE 8080

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/*.jar /app/app.jar

ENTRYPOINT ["java", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]