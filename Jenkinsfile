pipeline {
    agent any
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
                        configName: 'localhost',
                        verbose: true,
                        transfers: [
                            sshTransfer(
                              sourceFiles: '**/*spring-test-0.0.1-SNAPSHOT.jar',
                              execCommand: 'mv deployJenkins/target/spring-test-0.0.1-SNAPSHOT.jar deployJenkins/target/spring-test-0.0.1-SNAPSHOT.jar'
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