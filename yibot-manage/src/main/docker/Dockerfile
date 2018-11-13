FROM java:8
VOLUME /data/app/yibot
WORKDIR /data/app/yibot
ADD yibot-manage.jar yibot-manage.jar
EXPOSE 8102
ENTRYPOINT ["java","-Duser.timezone=GMT+08","-Xms2048m","-Xmx2048m","-server","-jar","yibot-manage.jar"]