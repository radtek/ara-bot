pipeline {
    agent none
    parameters {
        string(name: 'env', defaultValue: 'master', description: '指定要发布的环境，dev/test/master')
        string(name: 'owner', defaultValue: 'jbot', description: '指定用户为:jbot')
        string(name: 'version', defaultValue: 'V1.0.1', description: '指定发布的版本为: V1.0.1')
        string(name: 'apiModule', defaultValue: 'yibot-api', description: '指定要发布的模块名称，yibot-api')
        string(name: 'manageModule', defaultValue: 'yibot-manage', description: '指定要发布的模块名称，yibot-manage')
        string(name: 'apiPortLan', defaultValue: '8101', description: '指定要绑定的内部端口，8101')
        string(name: 'apiPortWan', defaultValue: '10101', description: '指定要绑定的内部端口，10101')
        string(name: 'managePortLan', defaultValue: '8102', description: '指定要绑定的外部端口，8102')
        string(name: 'managePortWan', defaultValue: '10102', description: '指定要绑定的外部端口，10102')
    }
    stages{
        stage('init') {
            agent any
            steps {
                script{
                    PJNAME = 'yibot'
                    API = 'api'
                    MANAGE  = 'manage'
                    DOCKER_DIR = 'docker'
                    DOCKER_REGISTRY = '10.4.0.9:5000/'
                    API_NAME = "${PJNAME}-${API}"
                    MANAGE_NAME = "${PJNAME}-${MANAGE}"
                    LOG_PATH = '/home/jbot/data/log/yibot'
                    if(env.BRANCH_NAME.contains('master')){
                        LOG_PATH = '/home/dev/log/yibot'
                        TYPE = 'prod'
                        DOCKER_REGISTRY = 'ccr.ccs.tencentyun.com/'
                        DOCKER_SERVER = 'ccr.ccs.tencentyun.com'
                        NAMESPACE = 'zhuiyi_docker_list'
                        CREDENTIALS = 'ab92b85c-f268-432a-b0ad-1c378817798d'
                    } else {
                        if(params.env == 'test'){
                            DEPLOY_SERVERS = ['10.4.1.106']
                            USER = params.owner
                            TYPE = 'test'
                            CREDENTIALS = 'ab92b85c-f268-432a-b0ad-1c378817798d'
                        }else if(params.env == 'dev') {
                            DEPLOY_SERVERS = ['192.168.1.167']
                            USER = params.owner
                            TYPE = 'dev'
                            CREDENTIALS = '6a8a5c43-250a-40e0-bafd-c3b92bdf31fb'
                            DOCKER_REGISTRY = ''
                        }
                    }

                }
                sh "rm -rf ${DOCKER_DIR} && mkdir ${DOCKER_DIR} && mkdir ${DOCKER_DIR}/${params.owner}"
                sh "mkdir ${DOCKER_DIR}/${params.owner}/${params.apiModule}"
                sh "mkdir ${DOCKER_DIR}/${params.owner}/${params.manageModule}"
            }
        }
        stage('package'){
            agent {
                docker {
                    image 'maven:3.5.3-jdk-8'
                    args '-v /var/jenkins_home/docker/data/maven/root/.m2:/root/.m2'
                }
            }
            steps {
                sh "mvn clean -pl ${params.apiModule} -am -Dmaven.test.skip=true install"
                sh "cp -f ${params.apiModule}/target/*.jar ${DOCKER_DIR}/${params.owner}/${params.apiModule}"
                sh "cp -f ${params.apiModule}/src/main/docker/Dockerfile ${DOCKER_DIR}/${params.owner}/${params.apiModule}"

                sh "mvn clean -pl ${params.manageModule} -am -Dmaven.test.skip=true install"
                sh "cp -f ${params.manageModule}/target/*.jar ${DOCKER_DIR}/${params.owner}/${params.manageModule}"
                sh "cp -f ${params.manageModule}/src/main/docker/Dockerfile ${DOCKER_DIR}/${params.owner}/${params.manageModule}"
            }
        }
        stage('build-docker'){
            agent any
            steps {
                sh "docker build -f ${DOCKER_DIR}/${params.owner}/${params.apiModule}/Dockerfile -t ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version} ${DOCKER_DIR}/${params.owner}/${params.apiModule}/"
                sh "docker build -f ${DOCKER_DIR}/${params.owner}/${params.manageModule}/Dockerfile -t ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version} ${DOCKER_DIR}/${params.owner}/${params.manageModule}/"
            }
        }
        stage('push-docker'){
            when {
                expression { params.env != 'dev' }
            }
            agent any
            steps {
                script{
                    if(params.env == 'master'){
                         sh "docker login --username=100006863759 ${DOCKER_SERVER} -p 2758c820ebe9"
                         sh "docker tag ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version} ${DOCKER_REGISTRY}${NAMESPACE}/${params.apiModule}:${TYPE}${params.version}"
                         sh "docker push ${DOCKER_REGISTRY}${NAMESPACE}/${params.apiModule}:${TYPE}${params.version}"
                         sh "docker tag ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version} ${DOCKER_REGISTRY}${NAMESPACE}/${params.manageModule}:${TYPE}${params.version}"
                         sh "docker push ${DOCKER_REGISTRY}${NAMESPACE}/${params.manageModule}:${TYPE}${params.version}"
                    }else{
                         sh "docker push ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version}"
                         sh "docker push ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version}"
                    }
                }
            }
        }
        stage('deploy'){
            agent any
            steps{
                sshagent(credentials: [CREDENTIALS]){
                    script{
                        if(params.env != 'master'){
                            for(String DEPLOY_SERVER:DEPLOY_SERVERS){
                                if(params.env == 'test'){
                                    sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker rmi -f ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version} || echo 0"
                                    sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker rmi -f ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version} || echo 0"
                                    sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker pull ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version}"
                                    sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker pull ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version}"
                                }
                                sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker rm -f ${params.apiModule} || echo 0"
                                sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker rm -f ${params.manageModule} || echo 0"
                                sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker run -d -p ${params.apiPortWan}:${params.apiPortLan} --name ${params.apiModule} -e SPRING_PROFILES_ACTIVE=${params.env} -v ${LOG_PATH}/${params.apiModule}:/data/log ${DOCKER_REGISTRY}${params.apiModule}:${TYPE}${params.version}"
                                sh "ssh -o StrictHostKeyChecking=no ${USER}@${DEPLOY_SERVER} docker run -d -p ${params.managePortWan}:${params.managePortLan} --name ${params.manageModule} -e SPRING_PROFILES_ACTIVE=${params.env} -v ${LOG_PATH}/${params.manageModule}:/data/log ${DOCKER_REGISTRY}${params.manageModule}:${TYPE}${params.version}"
                            }
                        }
                    }

                }
            }
        }
    }
}