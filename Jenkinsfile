pipeline {
    agent any
    environment {
      dockerImage = ''
      registry = "docker-repo/spring-test"
      registryCredential = 'nexus-creds'
    }
    stages{

      stage('Docker Build') { 
        steps{
          script{
            docker.image('maven:3.6-jdk-8').inside ('-v $HOME/.m2:/root/.m2'){
          
            stage('Build'){
              git 'https://github.com/yakketyyak/jenkins.git'
              sh '''
                 mvn -B -DskipTests clean package -X
                 mvn dependency:analyze-duplicate
              '''
              /*retry(3){
                sh 'mvn -v'
              }*/
            }

            stage('Test'){
              sh '''
                mvn test
              '''
              archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
      }
     }
      
    }

    stage('Deploy'){
      when {
        branch 'master'
      }

      parallel {

         stage('Deploy on nexus'){

      environment {
        artifactId = readMavenPom().getArtifactId()
        version = readMavenPom().getVersion()
        packaging = readMavenPom().getPackaging()
        nexusUrl = "localhost:8081"
      }
      steps([$class: 'NexusArtifactUploader']){
        //nexusPublisher nexusInstanceId: 'nexus-localhost', nexusRepositoryId: 'maven-snapshots', packages: [], tagName: 'v1.0'
        //nexusArtifactUploader artifacts: [[artifactId: 'spring-test', classifier: '', file: 'target/spring-test-0.0.1-SNAPSHOT.jar', type: 'jar']], credentialsId: 'nexus-creds', groupId: 'ci.pabeu', nexusUrl: 'localhost:8081', nexusVersion: 'nexus3', protocol: 'http', repository: 'maven-releases', version: 'v1.0'
        // "${} pour récupérer une varaiable"
        nexusArtifactUploader(
          artifacts: [
            [
              artifactId: "${env.artifactId}", 
              classifier: '', 
              file: "target/${env.artifactId}-${env.version}.${env.packaging}", 
              type: "${env.packaging}"
            ]

          ],
          credentialsId: 'nexus-creds', 
          groupId: 'ci.pabeu', 
          nexusUrl: "${env.nexusUrl}", 
          nexusVersion: 'nexus3', 
          protocol: 'http', 
          repository: 'maven-releases', 
          version: '1.0.2'

        )
        sha1 "target/${env.artifactId}-${env.version}.${env.packaging}"
      }
    }

    stage('Deploy docker image'){
      environment {
        version = readMavenPom().getVersion()
        registryUrl = 'http://localhost:8123'
      }
      steps{
        //docker rmi $(docker images --filter=reference="spring-test:0.0.1-SNAPSHOT" -q)
        /*sh'''
          docker build -t spring-test:0.0.1-SNAPSHOT -f Dockerfile .
          docker login -u admin -p admin localhost:8123
          docker tag spring-test:0.0.1-SNAPSHOT localhost:8123/docker-repo/spring-test:latest
          docker push localhost:8123/docker-repo/spring-test:latest
         '''*/
         //docker logout localhost:8123
         script{
            dockerImage = docker.build registry
            docker.withRegistry(registryUrl, registryCredential ) {
            dockerImage.push('latest')
           }
        }
      }
    }
    }
    }
    
    stage('Zip file'){
       when {
        branch 'master'
      }
      steps{
          zip(
            archive: true, 
            dir: '', 
            glob: 'target/spring-test*.jar', 
            zipFile: 'spring-test.zip'
            ) 
        }
    }
  } 
}