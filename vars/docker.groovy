def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/raghudevopsb63/${COMPONENT}"

    stage('Docker Build') {
      sh "docker build -t 633788536644.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest ."
    }

    if (env.TAG_NAME != null) {
      stage('Docker Build') {
        sh """
          docker tag 633788536644.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:latest 633788536644.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}
          aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 633788536644.dkr.ecr.us-east-1.amazonaws.com
          docker push 633788536644.dkr.ecr.us-east-1.amazonaws.com/${COMPONENT}:${TAG_NAME}
        """
      }
    }

  }
}
