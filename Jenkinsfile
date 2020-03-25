pipeline {
    agent any
    environment {
      ARTIFACTID = readMavenPom().getArtifactId()
      VERSION = readMavenPom().getVersion()
    }
    stages{

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
      steps([$class: 'NexusArtifactUploader']){
        //nexusPublisher nexusInstanceId: 'nexus-localhost', nexusRepositoryId: 'maven-snapshots', packages: [], tagName: 'v1.0'
        //nexusArtifactUploader artifacts: [[artifactId: 'spring-test', classifier: '', file: 'target/spring-test-0.0.1-SNAPSHOT.jar', type: 'jar']], credentialsId: 'nexus-creds', groupId: 'ci.pabeu', nexusUrl: 'localhost:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: 'v1.0'
        nexusArtifactUploader(
          artifacts: [
            [
              artifactId: '${ARTIFACTID}', 
              classifier: '', 
              file: 'target/${ARTIFACTID}-${VERSION}.jar', 
              type: 'jar'
            ]

          ],
          credentialsId: 'nexus-creds', 
          groupId: 'ci.pabeu', 
          nexusUrl: 'localhost:8081', 
          nexusVersion: 'nexus3', 
          protocol: 'http', 
          repository: 'maven-releases', 
          version: 'v1.0'

        )
      }
    }

    stage('Deploy docker image'){
      steps{
        sh'''
          docker rmi $(docker images --filter=reference="${ARTIFACTID}:${VERSION}" -q)
          docker build -t ${ARTIFACTID}:${VERSION} -f Dockerfile .
          docker login -u admin -p admin localhost:8123
          docker tag ${ARTIFACTID}:${VERSION} localhost:8123/docker-repo/spring-test:latest
          docker push localhost:8123/docker-repo/spring-test:latest
          docker logout localhost:8123
         '''
      }
    }
  } 
}