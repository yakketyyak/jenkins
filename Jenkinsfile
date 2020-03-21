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
    }
}