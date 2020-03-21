pipeline {
   
	 agent {
        docker {
            image 'maven:3.6.3-jdk-8'
            args '-v $HOME/.m2:/root/.m2'
        }
    }
   environment {
    //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
    IMAGE = readMavenPom().getArtifactId()
    VERSION = readMavenPom().getVersion()
    }

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
           steps {  
            sh 'mvn deploy' 
            }        
        }

      
    }
}