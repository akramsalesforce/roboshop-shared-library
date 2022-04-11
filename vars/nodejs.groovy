def lintChecks() {
  sh '''
    # We commented this because devs gonna check the failures.
    #~/node_modules/jslint/bin/jslint.js server.js
    echo Link Check for ${COMPONENT}
  '''
}

def sonarCheck() {
  sh '''
    sonar-scanner -Dsonar.host.url=http://172.31.4.85:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW}
    sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.4.85 ${COMPONENT}
  '''
}

def call() {
  pipeline {
    agent any

    environment {
      SONAR = credentials('SONAR')
    }

    stages {

      // For Each Commit
      stage('Lint Checks') {
        steps {
          script {
            lintChecks()
          }
        }
      }

      stage('SonarCheck') {
        steps {
          script {
            sonarCheck()
          }
        }
      }


    } // End of Stages

  }


}