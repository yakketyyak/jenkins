node {
    def IMAGE = readMavenPom().getArtifactId()
    def VERSION = readMavenPom().getVersion()

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
    stage('SSH transfer'){
          steps([$class: 'BapSshPromotionPublisherPlugin']){
            sshPublisher(
                continueOnError: false, failOnError: true,
                publishers: [
                    sshPublisherDesc(
                        configName: "${SSH_LOCAL_HOST}",
                        verbose: true,
                        transfers: [
                            sshTransfer(
                              sourceFiles: "**/*${IMAGE}-${VERSION}.jar",
                              execCommand: "mv deployJenkins/target/${IMAGE}-${VERSION}.jar deployJenkins/target/${IMAGE}-${VERSION}-${BUILD_TIMESTAMP}.jar"
                            )
                        ],
                        useWorkspaceInPromotion: true,
                        usePromotionTimestamp: true
                    )
                ]
            )
          }
        }

    // Clean up workspace
    //step([$class: 'WsCleanup'])
}