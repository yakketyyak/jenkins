pipeline {
   
	 agent any
    stages {

        stage('Build') { 
          steps {
            withMaven(
              maven: 'maven-3.6.3',
              mavenLocalRepo: '.repository'){
              sh 'mvn -B -DskipTests clean package' 
              }
            }
        }

        stage('Test') { 
		   
            steps {
               withMaven(maven: 'maven-3.6.3',
                mavenLocalRepo: '.repository'){
              sh 'mvn test' 
              }
            }
            
        }

        stage('Deploy') {           
           steps {                
           	withMaven(maven: 'maven-3.6.3',
              mavenLocalRepo: '.repository'){
              sh 'mvn deploy' 
              }
            }        
        }

        stage('Build docker image') {    
           
           steps {                
             sh "docker build -t spring-test:${POM_VERSION} -f Dockerfile ."
            }        
        }
    }
}