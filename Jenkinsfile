node {

    stage('Main Build') { 
      docker.image('maven:3.6-jdk-8').inside ('-v $HOME/.m2:/root/.m2'){
      	
      	stage('Build'){
			    git 'https://github.com/yakketyyak/jenkins.git'
	        sh '''
	           mvn -v
	           mvn -B -DskipTests clean package
	        '''
      	}

      	stage('Test'){
      		sh '''
            mvn test
            echo $WORKSPACE
          '''
      	}
      }
    }

    // Clean up workspace
    //step([$class: 'WsCleanup'])
}