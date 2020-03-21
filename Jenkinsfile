pipeline {
   
    agent { label 'docker' }

    stages {

        stage('Build') { 
          agent {
                docker {
                  label 'docker'  // both label and image
                  image 'maven:3.6.3-jdk-8'
                }
            }
          steps {
             sh 'mvn -B -DskipTests clean package' 
            }
        }

        stage('Test') { 
		        agent {
                docker {
                  label 'docker'  // both label and image
                  image 'maven:3.6.3-jdk-8'
                }
            }
            steps {
               sh 'mvn test' 
            }
            
        }

        stage('Deploy') { 
          agent {
                  docker {
                    label 'docker'  // both label and image
                    image 'maven:3.6.3-jdk-8'
                  }
              }          
           steps {  
            sh 'mvn deploy' 
            }        
        }

      
    }
}