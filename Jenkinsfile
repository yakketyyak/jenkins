pipeline {
   
    agent {
          docker {
            image 'maven:3.6.3-jdk-8'
            args '-v /root/.m2:/root/.m2'
          }
    }

    stages {

        stage('Build') { 
          steps {
            sh 'java --version'
            sh 'mvn -B -DskipTests clean package' 
          }
        }

        stage('Test') { 
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