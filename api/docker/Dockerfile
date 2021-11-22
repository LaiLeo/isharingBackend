FROM openjdk:8-jre-slim

ENV JAVA_OPTS="-XX:+UseG1GC -Xmx512m -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Duser.timezone=Asia/Taipei -Djava.security.egd=file:/dev/./urandom"

RUN echo "Asia/Taipei" > /etc/timezone
WORKDIR /app

COPY docker/entrypoint.sh /app/entrypoint.sh
RUN chmod +x /app/entrypoint.sh

EXPOSE 8080

COPY build/libs/ishareingApi.jar /app/ishareingApi.jar

ENTRYPOINT ["./entrypoint.sh"]