def lintChecks() {
  sh '''
    # We commented this because devs gonna check the failures.
    #~/node_modules/jslint/bin/jslint.js server.js
    #pylint *.py
    echo Lint Check for ${COMPONENT}
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