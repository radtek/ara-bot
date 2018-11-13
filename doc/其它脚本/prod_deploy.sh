#!/bin/bash
app_res=ccr.ccs.tencentyun.com/zhuiyi_docker_list
app_version_old=prodV1.0.1
app_version_new=prodV1.0.1
app_array=(yibot-manage yibot-api)
echo "---------开始发布---------------"
echo "1.开始清理应用服务---------------"
for app in ${app_array[@]}
do
    docker stop $app
	docker rm $app
	echo "清理应用服务 $app 成功"
done
echo "2.开始清理应用镜像---------------"
if [ $app_version_old == $app_version_new ]
then
	for app in ${app_array[@]}
	do
		docker rmi $app_res/$app:$app_version_old
		echo "清理旧应用镜像 $app 成功"
	done
else
    echo "跳过清理旧应用镜像"
fi
echo "3.拉取新镜像并部署启动---------------"
docker login --username=100006863759 ccr.ccs.tencentyun.com --password-stdin 2758c820ebe9
for app in ${app_array[@]}
do
	docker pull $app_res/$app:$app_version_new
	if [[ $app =~ "api" ]]
	then
		docker run -d -p 10101:8101 --name $app -e SPRING_PROFILES_ACTIVE=pro -v /data/log/$app:/data/log ccr.ccs.tencentyun.com/zhuiyi_docker_list/$app:$app_version_new
	elif [[ $app =~ "manage" ]]
	then
		docker run -d -p 10102:8102 --name $app -e SPRING_PROFILES_ACTIVE=pro -v /data/log/$app:/data/log ccr.ccs.tencentyun.com/zhuiyi_docker_list/$app:$app_version_new
	else
		echo "未定义的应用服务"
	fi
	echo "启动服务 $app 成功"
done
echo "---------结束发布---------------"