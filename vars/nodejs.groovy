def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/akramsaleforce/${COMPONENT}.git"


    env.APP_TYPE = "nodejs"
    common.lintChecks()
    env.ARGS="-Dsonar.sources=."
    common.sonarCheck()
    common.testCases()


  }
}
