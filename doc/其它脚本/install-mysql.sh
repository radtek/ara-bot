#!/usr/bin/env bash
mysql_version=mysql:5.7
user_password=123.com
docker pull $mysql_version
docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=$user_password --name mysql -d --restart=always --privileged=true $mysql_version
#docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123.com --name mysql -d --restart=always --privileged=true  -v=/data/soft/mysql/1/data:/var/lib/mysql mysql:5.7
docker start mysql
#expect -c "
#        spawn docker exec -it mysql bash;
#        expect \"*root@*\"  { send \"mysql -uroot -p$user_password\r\"}
#        send \"grant all privileges on *.* to root@"%" identified by "$user_password" with grant option \r\"
#        send \"flush privileges \r\"
#        set timeout 5
#        send \"exit\r\"
#        "
firewall-cmd --zone=public --add-port=3306/tcp --permanent
firewall-cmd --reload
echo "安装mysql成功"
