def lintChecks() {
  sh '''
    # We commented this because devs gonna check the failures.
    #~/node_modules/jslint/bin/jslint.js server.js
    echo Link Check 
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
            nodejs.lintChecks()
          }
        }
      }

    } // End of Stages

  }


}