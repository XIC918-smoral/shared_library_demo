def call() {
node {
//     stage('git checkout') { // for display purposes
//         // Get some code from a GitHub repository
//         git branch: 'main', url: 'https://github.com/XIC918-smoral/ec2.git'
        
//     }
    
//     stage('Terraform init') {
//        sh 'terraform init'
//     }
        
        stage('copy'){
       copyArtifacts fingerprintArtifacts: true, includeBuildNumberInTargetPath: true, projectName: 'terraform', selector: workspace()
        }
//      stage('Approval') {
//             // no agent, so executors are not used up when waiting for approvals
//                 script {
//                     def deploymentDelay = input id: 'Deploy', message: 'Deploy to production?', submitter: 'rkivisto,admin', parameters: [choice(choices: ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16', '17', '18', '19', '20', '21', '22', '23', '24'], description: 'Hours to delay deployment?', name: 'deploymentDelay')]
//                     sleep time: deploymentDelay.toInteger(), unit: 'HOURS'
//                 }
//      }
    
    stage('Terraform apply') {
       sh 'terraform apply plan.out'
    }
}
}
