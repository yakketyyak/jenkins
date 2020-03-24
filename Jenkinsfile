node {

    def SSH_LOCAL_HOST = 'localhost'
    def fileName = ${POM_ARTIFACTID}-${POM_VERSION}.${POM_PACKAGING}
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
                              sourceFiles: "**/*${fileName}",
                              execCommand: "mv deployJenkins/target/${fileName} deployJenkins/target/${fileName}"
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