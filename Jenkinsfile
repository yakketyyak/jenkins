pipeline {

	 agent any

    stages {

        stage('Build') { 
            withMaven(maven: 'maven'){
              sh 'mvn -B -DskipTests clean package' 
              }
        }

        stage('Test') { 
		   
            steps {
               withMaven(maven: 'maven'){
              sh 'mvn test' 
              }
            }
            
        }

        stage('Deploy') { 
               when {              
               	expression {                
               		currentBuild.result == null || currentBuild.result == 'SUCCESS' 

                }            
              }            
           steps {                
           	withMaven(maven: 'maven'){
              sh 'mvn deploy' 
              }
            }        
        }
    }
}