env.APP_TYPE = "nodejs"

def call() {
  node {
    common.lintChecks()
    env.ARGS="-Dsonar.sources=."
    common.sonarCheck()
    common.testCheck()
  }
}