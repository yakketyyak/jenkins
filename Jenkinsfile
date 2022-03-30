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
                 mvn -B -DskipTests clean package
              '''
              retry(3){
                sh 'mvn -v'
              }
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
        
        
        import glob
import json
import sys
from datetime import datetime

from openapi_spec_validator import validate_spec

OPEN_API_SPEC_KEYWORD = 'openapi'


def validate_api_version(version):
    try:
        datetime.strptime(version, '%Y-%m')
    except ValueError:
        raise ValueError('Incorrect api version format, should be yyyy-mm found : {}'.format(version))


def validate_open_api_file():
    for filename in glob.iglob('domains/**', recursive=True):
        if filename.lower().endswith('json'):
            with open(filename, 'r') as json_file:
                open_api_spec = json.load(json_file)
                validate_spec(open_api_spec)
                open_api_info = open_api_spec.get('info')
                if open_api_info is None:
                    print('API info must be set')
                    sys.exit(1)
                api_version = open_api_info.get('version')
                validate_api_version(api_version)


if __name__ == '__main__':
    validate_open_api_file()

  } 
}
requirements.txt
openapi-spec-validator
https://nordicapis.com/8-openapi-linters/
