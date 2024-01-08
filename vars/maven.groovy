def call() {
  node {
    sh 'rm -rf *'
    git branch: 'main', url: "https://github.com/akramsalesforce/${COMPONENT}"
    env.APP_TYPE = "maven"
    common.lintChecks()
    sh 'mvn clean compile'
    env.ARGS="-Dsonar.java.binaries=target/"
    common.sonarCheck()

  }
}
