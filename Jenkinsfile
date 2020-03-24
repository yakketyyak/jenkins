node {

    stage('Main Build') { 
      docker.image('maven:3.3.3-jdk-8').inside {
      	
      	stage('Build'){
			git 'https://github.com/yakketyyak/jenkins.git'
	        writeFile file: 'settings.xml', text: "<settings><localRepository>${pwd()}/.m2repo</localRepository></settings>"
	        sh '''
	           mvn -v
	           mvn -B -s settings.xml -DskipTests clean package
	        '''
      	}

      	stage('Test'){
      		sh 'mvn test'
      	}
      }
    }

    // Clean up workspace
    step([$class: 'WsCleanup'])
}