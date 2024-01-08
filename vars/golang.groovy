def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/akramsalesforce/${COMPONENT}"
    env.APP_TYPE = "golang"
    common.lintChecks()
    env.ARGS="-Dsonar.java.binaries=."
    common.sonarCheck()

  }
}
