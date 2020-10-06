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
            stage('Build Docker Image'){
            
            steps{
                script {
                    DOCKER_IMAGE = docker.build "${REGISTRY}/${PROJECT_NAME}:dev"
                    DOCKER_IMAGE.push()
                    sh 'sleep 30'
                }
            }
            }
}
}
