pipeline {
   
    agent any

    stages {

        stage('Build') { 
          agent {
            label 'maven'
          }
          steps {
            sh 'java --version'
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
           steps {  
            sh 'mvn deploy' 
           }        
        }
    }
}