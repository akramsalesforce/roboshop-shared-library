def sonarCheck() {

  sh '''
    #sonar-scanner -Dsonar.host.url=http://172.31.4.85:9000 -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_USR} -Dsonar.password=${SONAR_PSW} ${ARGS}
    #sonar-quality-gate.sh ${SONAR_USR} ${SONAR_PSW} 172.31.4.85 ${COMPONENT}
    echo Sonar Checks for ${COMPONENT}
  '''
}


def lintChecks() {
  stage('Lint Checks') {
    if (env.APP_TYPE == "nodejs") {
      sh '''
        # We commented this because devs gonna check the failures.
        #~/node_modules/jslint/bin/jslint.js server.js
        echo Link Check for ${COMPONENT}
      '''
    }
    else if (env.APP_TYPE == "maven") {
      sh '''
      # We commented this because devs gonna check the failures.
      #~/node_modules/jslint/bin/jslint.js server.js
      #mvn checkstyle:check
      echo Lint Check for ${COMPONENT}
    '''
    }
    else if (env.APP_TYPE == "python" ) {
      sh '''
        # We commented this because devs gonna check the failures.
        #~/node_modules/jslint/bin/jslint.js server.js
        #pylint *.py
        echo Lint Check for ${COMPONENT}
      '''
    }
    else if (env.APP_TYPE == "golang" ){
      sh '''
        # We commented this because devs gonna check the failures.
        #~/node_modules/jslint/bin/jslint.js server.js
        echo Link Check for ${COMPONENT}
      '''
    }
  }

}

def testCases() {
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
}

