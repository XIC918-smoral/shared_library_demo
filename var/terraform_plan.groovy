def call() {
node {
    stage('git checkout') { // for display purposes
        // Get some code from a GitHub repository
        git branch: 'main', url: 'https://github.com/XIC918-smoral/ec2.git'
        // Get the Maven tool.
        // ** NOTE: This 'M3' Maven tool must be configured
        // **       in the global configuration.
        //mvnHome = tool 'M3'
    }
    stage('tflint'){
    echo "=========================Executing linting for Terraform files============================"
    sh  'sudo docker run --rm -v $(pwd):/data -t ghcr.io/terraform-linters/tflint'
   
    }
    
    stage('checov'){
    echo "=========================Executing checkov for Terraform files============================"
    sh  'sudo docker pull bridgecrew/checkov'
    sh 'sudo docker run --rm --volume $(pwd):/tf --workdir /tf bridgecrew/checkov --directory /tf'
    }
    
    
    
    // stage('tfsec'){
    // echo "=========================Executing code compliance for Terraform files============================"
    // sh  'sudo docker run --rm -v "$(pwd):/src" aquasec/tfsec /src'
   
    // }
   
    
    
    
 


    stage('Terraform init') {
       sh 'terraform init'
    }
   stage('Terraform fmt') {
       sh 'terraform fmt'
    }
    stage('Terraform validate') {
        sh 'terraform validate'
    }
    stage('Terraform plan') {
        sh 'terraform plan -out=plan.out'
    }
    
    
    
    
    stage('infracost'){
    echo "=========================Executing infracost============================"
    sh  'terraform show -json plan.out > plan.json'
    sh 'sudo docker run --rm   -e INFRACOST_API_KEY=ico-PdUKKbuT5d6Com9gKcccnWhWZXId6YWB  -v "$(pwd):/src" infracost/infracost breakdown --path  /src/plan.json --show-skipped'
   
    }
    
    stage('infracost difference'){
    echo "=========================Executing infracost difference============================"
    sh  'terraform show -json plan.out > plan.json'
    sh 'sudo docker run --rm   -e INFRACOST_API_KEY=ico-PdUKKbuT5d6Com9gKcccnWhWZXId6YWB  -v "$(pwd):/src" infracost/infracost diff --path  /src/plan.json --show-skipped'
   }
   
   
}
}
