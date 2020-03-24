node {

    stage('Build') { 
      docker.image('maven:3.3.3-jdk-8').inside {
        git 'https://github.com/yakketyyak/jenkins.git'
        sh '''
           mvn -v
           mvn -B -DskipTests clean package
        '''
      }
    }
}