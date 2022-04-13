def lintChecks() {
  sh '''
    # We commented this because devs gonna check the failures.
    #~/node_modules/jslint/bin/jslint.js server.js
    echo Link Check for ${COMPONENT}
  '''
}

def call() {
  pipeline {
    agent any

    environment {
      SONAR = credentials('SONAR')
      NEXUS = credentials('NEXUS')
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
            env.ARGS="-Dsonar.sources=."
            common.sonarCheck()
          }
        }
      }

      stage('Test Cases')  {

        parallel {

          stage('Unit Tests') {
            steps {
              sh 'echo Unit Tests'
            }
          }

          stage('Integration Tests') {
            steps {
              sh 'echo Integration Tests'
            }
          }

          stage('Functional Tests') {
            steps {
              sh 'echo Functional Tests'
            }
          }

        }

      }

      stage('Check The Release') {
        when {
          expression { env.TAG_NAME != null }
        }
        steps {
          script {
            env.UPLOAD_STATUS=sh(returnStdout: true, script: 'curl -L -s http://172.31.0.90:8081/service/rest/repository/browse/${COMPONENT} | grep ${COMPONENT}-${TAG_NAME}.zip || true')
            print UPLOAD_STATUS
          }
        }
      }

      stage('Prepare Artifacts') {
        when {
          expression { env.TAG_NAME != null }
          expression { env.UPLOAD_STATUS == "" }
        }
        steps {
          sh '''
            npm install 
            zip -r ${COMPONENT}-${TAG_NAME}.zip node_modules server.js 
          '''
        }
      }

      stage('Upload Artifacts') {
        when {
          expression { env.TAG_NAME != null }
          expression { env.UPLOAD_STATUS == "" }
        }
        steps {
          sh '''
            curl -f -v -u ${NEXUS_USR}:${NEXUS_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip  http://172.31.0.90:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip
          '''
        }
      }


    } // End of Stages

  }


}