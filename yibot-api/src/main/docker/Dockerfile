FROM java:8
VOLUME /data/app/yibot
WORKDIR /data/app/yibot
ADD yibot-api.jar yibot-api.jar
EXPOSE 8101
ENTRYPOINT ["java","-Duser.timezone=GMT+08","-Xms2048m","-Xmx2048m","-server","-jar","yibot-api.jar"]