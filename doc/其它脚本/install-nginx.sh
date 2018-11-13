#!/usr/bin/env bash
nginx_version=nginx:latest
docker pull $nginx_version
docker run --name nginx -p 443:443 -p 80:80 -d --restart=always -v=/data/dev/soft/nginx/config/nginx.conf:/etc/nginx/nginx.conf  -v=/data/log/nginx:/data/log/nginx/ -v=/data/dev/soft/nginx/ssl:/ssl/ -v=/data/dev/soft/nginx/data:/usr/share/nginx/html/  $nginx_version
docker start redis
firewall-cmd --zone=public --add-port=443/tcp --permanent
firewall-cmd --zone=public --add-port=80/tcp --permanent
firewall-cmd --reload
echo "安装nginx成功"