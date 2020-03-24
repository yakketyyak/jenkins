pipeline {
    agent any
    environment {
    //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
      pom = readMavenPom file: 'pom.xml'
      artifactId = 'pom.artifactId'
      version    = 'pom.version' 
      packaging  = 'pom.packaging'
      FILE_NAME  = artifactId + '-' + version + packaging
      SSH_LOCAL_HOST = 'localhost'
    }

    stages
    {
      stage('Docker Build') { 
      steps{
        script{
          docker.image('maven:3.6-jdk-8').inside ('-v $HOME/.m2:/root/.m2'){
        
          stage('Build'){
            git 'https://github.com/yakketyyak/jenkins.git'
            sh '''
               mvn -v
               mvn -B -DskipTests clean package
            '''
          }

          stage('Test'){
            sh '''
              mvn test
              echo $WORKSPACE
            '''
          }
      }
    }
   }
      
  }
    stage('SSH transfer'){
          steps([$class: 'BapSshPromotionPublisherPlugin']){
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: '${SSH_LOCAL_HOST}',
                        verbose: true,
                        transfers: [
                            sshTransfer(
                              sourceFiles: '**/*${FILE_NAME}',
                              execCommand: 'mv deployJenkins/target/${FILE_NAME} deployJenkins/target/${FILE_NAME}'
                            )
                        ],
                        useWorkspaceInPromotion: true,
                        usePromotionTimestamp: true
                    )
                ]
            )
          }
    }
   } 
}