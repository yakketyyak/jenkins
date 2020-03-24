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

    stage('Deploy on nexus'){
      steps{
        nexusPublisher nexusInstanceId: 'nexus-localhost', nexusRepositoryId: 'maven-snapshots', packages: [], tagName: 'v1.0'
      }
    }
}