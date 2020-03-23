pipeline {
   
   agent any
   environment {
    //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
    IMAGE = readMavenPom().getArtifactId()
    VERSION = readMavenPom().getVersion()
    //GITHUB_CREDS = credentials('github')
    SSH_LOCAL_HOST = 'localhost'
    }

    stages {

        stage('Build') { 
          steps {
            withMaven(
              maven: 'maven-3.6.3',
              mavenLocalRepo: '.repository'){
              sh 'echo ${IMAGE}'
              sh 'mvn -B -DskipTests clean package' 
              }
            }
        }

        stage('Test') { 
       
            steps {
               withMaven(maven: 'maven-3.6.3',
                mavenLocalRepo: '.repository'){
              sh 'mvn test' 
              }
            }
            
        }

        stage('Deploy') {           
           steps {                
            withMaven(maven: 'maven-3.6.3',
              mavenLocalRepo: '.repository'){
              sh 'mvn deploy' 
              }
            }        
        }

        stage('Build docker image') {    
           
           steps {                
             sh "docker build -t ${IMAGE}:${VERSION} -f Dockerfile ."
            }        
        }

        stage('SSH transfer'){
          steps([$class: 'BapSshPromotionPublisherPlugin']){
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "${SSH_LOCAL_HOST}",
                        verbose: true,
                        transfers: [
                            sshTransfer(
                              sourceFiles: "${IMAGE}-${VERSION}.jar",
                              remoteDirectory: ".",
                              execCommand: "ls -a  && java -jar ${IMAGE}-${VERSION}.jar"
                            )
                        ],
                        useWorkspaceInPromotion: true,
                        usePromotionTimestamp: true,
                        sshRetry: [
                          retries: 2,
                          retryDelay: 3600
                        ]
                    )
                ]
            )
          }
        }
    }
}