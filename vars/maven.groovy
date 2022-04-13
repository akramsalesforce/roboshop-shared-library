env.APP_TYPE = "maven"

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
            sh 'mvn clean compile'
            env.ARGS="-Dsonar.java.binaries=target/"
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

    } // End of Stages

  }


}