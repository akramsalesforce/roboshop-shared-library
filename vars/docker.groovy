def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/raghudevopsb63/${COMPONENT}"

    stage('Docker Build') {
      sh "docker build ."
    }

  }
}
