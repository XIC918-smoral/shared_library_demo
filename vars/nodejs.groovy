def call() {
node {


  stage('SCM') {
    git 'https://github.com/XIC918-smoral/simple-node-js-react-npm-app.git'
  }
  
  stage('SonarQube analysis') {
    def scannerHome = tool 'sonar_scanner';
    withSonarQubeEnv('localsonar') { // If you have configured more than one global server connection, you can specify its name
      sh "${scannerHome}/bin/sonar-scanner \
      -Dsonar.projectKey=nodejss \
      -Dsonar.projectName=nodejss"
    //   -Dsonar.jacoco.reportsPath=target/jacoco.exec \
    //   -Dsonar.java.checkstyle.reportPaths=target/checkstyle-result.xml"
    //   -Dsonar.java.binaries=src/test/java/com/visualpathit/account/controllerTest"
    }
  }
  stage('Example') {
                sh 'npm --version'
            }
            
    stage('scan'){
snykSecurity failOnIssues: false, snykInstallation: 'local_snyk', snykTokenId: '25c7eebd-e80f-49a8-821e-e2dd92d0cc7f'
}
  
  stage('build') {
        sh 'npm install'
      }

    stage('Test'){
                sh './jenkins/scripts/test.sh'
            }
stage('Deliver'){
                sh './jenkins/scripts/deliver.sh' 
                input message: 'Finished using the web site? (Click "Proceed" to continue)' 
                sh './jenkins/scripts/kill.sh' 
            }
}
}
