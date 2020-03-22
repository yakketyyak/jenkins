pipeline {
   
   agent any
   environment {
    //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
    IMAGE = readMavenPom().getArtifactId()
    VERSION = readMavenPom().getVersion()
    GITHUB_CREDS = credentials('github')
    }

    stages {

        stage('Build') { 
          steps {
            withMaven(
              maven: 'maven-3.6.3',
              mavenLocalRepo: '.repository'){
              sh '${env.GITHUB_CREDS}'
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
             sh "docker build -t spring-test:${VERSION} -f Dockerfile ."
            }        
        }
    }
}