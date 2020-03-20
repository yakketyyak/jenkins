pipeline {

	 agent any

    stages {

        stage('Build') { 
            steps {
                sh 'mvn -B -DskipTests clean package' 
            }
        }

        stage('Test') { 
		   
            steps {
                sh 'mvn test' 
            }
        }

        stage('Deploy') { 
               when {              
               	expression {                
               		currentBuild.result == null || currentBuild.result == 'SUCCESS' 

                }            
              }            
           steps {                
           	sh 'mvn deploy'           
           	  }        
           	}
    }
}