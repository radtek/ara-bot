#!/usr/bin/env bash
redis_version=redis:latest
docker pull $redis_version
docker run -p 6379:6379 --name redis -d --restart=always $redis_version redis-server --appendonly yes
docker start redis
firewall-cmd --zone=public --add-port=6379/tcp --permanent
firewall-cmd --reload
echo "安装redis成功"