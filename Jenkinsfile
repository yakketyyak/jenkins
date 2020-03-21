pipeline {
   
    agent none

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

        stage('Deploy') {   
           agent {
              label 'maven'
            }      
           steps {  
            sh 'mvn deploy' 
           }        
        }

        stage('Build image') {   
           agent any      
           steps {  
            sh 'docker build -t spring-test:${VERSION} -f Dockerfile .' 
           }        
        }
    }
}