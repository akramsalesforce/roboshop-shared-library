def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/akramsalesforce/${COMPONENT}"
    env.APP_TYPE = "nginx"
    common.lintChecks()
    env.ARGS="-Dsonar.sources=."
    common.sonarCheck()
    common.testCases()

    if (env.TAG_NAME != null) {
      common.artifacts()
    }
  }
}
