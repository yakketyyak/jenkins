pipeline {
   
	 agent any
    stages {

        stage('Build') { 
          steps {
            withMaven(
              maven: 'maven-3.6.3'){
              sh 'mvn -B -DskipTests clean package -Dmaven.repo.local=.m2' 
              }
            }
        }

        stage('Test') { 
		   
            steps {
               withMaven(maven: 'maven-3.6.3'){
              sh 'mvn test -Dmaven.repo.local=.m2' 
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
           	withMaven(maven: 'maven-3.6.3'){
              sh 'mvn deploy -Dmaven.repo.local=.m2' 
              }
            }        
        }
    }
}