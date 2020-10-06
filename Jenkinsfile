pipeline {
    agent {
                    label 'docker'
            }
    
    environment {
        REGISTRY = "registry.projectweek.be"
        PROJECT_NAME = env.GIT_URL.replaceFirst(/^.*\/(?:pw2-)?([^\/]+?).git$/, '$1')
        DOCKER_IMAGE = ''
    }
    stages {
            stage('Build Docker Image for staging'){
            
                steps{
                    script {
                        DOCKER_IMAGE = docker.build "${REGISTRY}/${PROJECT_NAME}:dev"
                        DOCKER_IMAGE.push()
                        sh 'sleep 10'
                    }
                }
            }
            
            stage('Deploy to staging'){
                steps {
                    script {
                        sh 'curl -X POST http://projectweek.be:9000/api/webhooks/b72aa1ac-8e90-433f-9389-0b74147c1fcd'
                    }
                }
            }

            
            stage("Build Docker Image for production"){
                when { branch 'master' }
                steps{
                    script {
                        DOCKER_IMAGE = docker.build "${REGISTRY}/${PROJECT_NAME}:latest"
                        DOCKER_IMAGE.push()
                        sh 'sleep 10'
                    }
                }
            }
            stage("Deploy to production"){
                when { branch 'master' }
                steps {
                    script {
                        sh 'curl -X POST http://projectweek.be:9000/api/webhooks/4263b39c-2aff-4818-9ef9-b38981d37c70'
                    }
                }
            }
            
    }
}
