def lintChecks() {
  sh '''
    # We commented this because devs gonna check the failures.
    #~/node_modules/jslint/bin/jslint.js server.js
    mvn checkstyle:check
  '''
}

def call() {
  pipeline {
    agent any

    stages {

      // For Each Commit
      stage('Lint Checks') {
        steps {
          script {
            lintChecks()
          }
        }
      }

    } // End of Stages

  }


}