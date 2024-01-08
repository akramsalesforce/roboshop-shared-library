def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/akramsalesforce/${COMPONENT}"
    env.APP_TYPE = "python"
    common.lintChecks()
    env.ARGS="-Dsonar.sources=."
    common.sonarCheck()
    common.testCases()


  }
}

