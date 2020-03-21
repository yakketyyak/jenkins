pipeline {
   
    agent { label 'docker' }

    stages {

        stage('Build') { 
          steps {
            sh 'docker --version'
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