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
              //globalMavenSettingsFilePath: '${user.home}/.m2/settings.xml',
              mavenLocalRepo: '.repository'){
              sh 'mvn -B -DskipTests clean package' 
              }
            }
        }

        stage('Test') { 
       
            steps {
               withMaven(maven: 'maven-3.6.3',
                //image docker
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
              sh 'echo ${WORKSPACE}'
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
                              //${WORKSPACE}/.repository/${IMAGE}-${VERSION}.jar
                              sourceFiles: "**/*${IMAGE}-${VERSION}.jar",
                              //removePrefix: "target",
                              //remoteDirectory: ".",
                              //execCommand: "java -jar **/${IMAGE}-${VERSION}.jar"
                              execCommand: "mv ${remoteDirectory}/target/${IMAGE}-${VERSION}.jar ${remoteDirectory}/target/${IMAGE}-${VERSION}-${BUILD_TIMESTAMP}.jar"
                            )
                        ],
                        useWorkspaceInPromotion: true,
                        usePromotionTimestamp: true,
                        /*sshRetry: [
                          retries: 2,
                          retryDelay: 3600
                        ]*/
                    )
                ]
            )
          }
        }
    }
}