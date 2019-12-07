pipeline {
    agent none
    environment {
        PROJECT_NAME = env.GIT_URL.replaceFirst(/^.*\/([^\/]+?).git$/, '$1')
    }
    stages {
        stage('Build') {
            agent {
                    label 'maven'
            }
            steps {
               sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Test') {
            agent {
                    label 'maven'
             }
            steps {
                sh 'mvn test'
            }
        }
        stage('Build Docker Container') {
            agent {
                dockerfile {
                    filename 'Dockerfile.build'
                    label "${PROJECT_NAME}"
                }
            }
        }
        stage('Publish over SSH') {
            steps {
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: 'projectweek',
                            transfers: [
                                sshTransfer(
                                    cleanRemote: false,
                                    excludes: '',
                                    execCommand: "echo 'PROJECT_NAME=${PROJECT_NAME}' > ${PROJECT_NAME}${BUILD_NUMBER}/.env && docker build -t ${PROJECT_NAME} -t registry.projectweek.be/${PROJECT_NAME} ${PROJECT_NAME}${BUILD_NUMBER}/ & docker push registry.projectweek.be/${PROJECT_NAME} && cd ${PROJECT_NAME}${BUILD_NUMBER} && docker-compose config | docker stack deploy --compose-file - ${PROJECT_NAME} && cd ../ && rm -rf ${PROJECT_NAME}${BUILD_NUMBER}",
                                    execTimeout: 120000,
                                    flatten: false,
                                    makeEmptyDirs: false,
                                    noDefaultExcludes: false,
                                    patternSeparator: '[, ]+',
                                    remoteDirectory: "${PROJECT_NAME}${BUILD_NUMBER}",
                                    remoteDirectorySDF: false,
                                    removePrefix: '',
                                    sourceFiles: '**'
                                )
                            ],
                            usePromotionTimestamp: false,
                            useWorkspaceInPromotion: false,
                            verbose: false
                        )
                    ]
                )
            }
        }
    }
    post {
        always {
            archiveArtifacts artifacts: 'target/*.war', fingerprint: true
            junit 'target/surefire-reports/*.xml'
        }
    }
}
