pipeline {
   
	 agent any
    stages {

        stage('Build') { 
          steps {
            withMaven(
              maven: 'maven-3.6.3',
              mavenLocalRepo: '~/.m2/repository'){
              sh 'mvn -B -DskipTests clean package' 
              }
            }
        }

        stage('Test') { 
		   
            steps {
               withMaven(maven: 'maven-3.6.3'
                mavenLocalRepo: '~/.m2/repository'){
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
           	withMaven(maven: 'maven-3.6.3'
              mavenLocalRepo: '~/.m2/repository'){
              sh 'mvn deploy' 
              }
            }        
        }
    }
}