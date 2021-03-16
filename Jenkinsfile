class Constants {

  static final String MASTER_BRANCH = 'master'

  static final String QA_BUILD = 'Debug'
  static final String RELEASE_BUILD = 'Release'

  static final String INTERNAL_TRACK = 'internal'
  static final String RELEASE_TRACK = 'release'
}

def getBuildType() {
  switch (env.BRANCH_NAME) {
    case Constants.MASTER_BRANCH:
      return Constants.RELEASE_BUILD
    default:
      return Constants.QA_BUILD
  }
}

def getTrackType() {
  switch (env.BRANCH_NAME) {
    case Constants.MASTER_BRANCH:
      return Constants.RELEASE_TRACK
    default:
      return Constants.INTERNAL_TRACK
  }
}

def isDeployCandidate() {
  return ("${env.BRANCH_NAME}" =~ /(develop|master)/)
}

pipeline {
  agent {
          docker { image 'androidsdk/android-30:latest' }
  }

  environment {
    appName = 'blockchain-browser'

    SIGNING_KEY_PASSWORD = credentials('keyPassword')
    KEY_ALIAS = credentials('keyAlias')
    SIGNING_KEYSTORE = credentials('keyStore')
    STORE_PASSWORD = credentials('storePassword')
  }

  options {
    // Stop the build early in case of compile or test failures
    skipStagesAfterUnstable()
  }

  stages {
    stage('Compile') {
      steps {
        // Compile the app and its dependencies
        sh './gradlew clean compileDebugSources'
      }
    }

//    stage('Run Tests') {
//      steps {
//        echo 'Running Tests'
//        script {
//          VARIANT = getBuildType()
//          sh "./gradlew test${VARIANT}UnitTest"
//        }
//        junit "**/TEST-*.xml"
//      }
//    }

    stage('Unit test') {
      steps {
        // Compile and run the unit tests for the app and its dependencies
        sh './gradlew testDebugUnitTest'

        // Analyse the test results and update the build result as appropriate
        junit '**/TEST-*.xml'

        // Fool Jenkins into thinking the tests results are new
       // sh 'find . -name "TEST-*.xml" -exec touch {} \\;'
        junit '**/build/test-results/test/TEST-*.xml'
      }
    }

    stage('Build Bundle') {
      when { expression { return isDeployCandidate() } }
      steps {
        echo 'Building'
        script {
          VARIANT = getBuildType()
          sh "./gradlew -PstorePass=${STORE_PASSWORD} -Pkeystore=${KEYSTORE} -Palias=${KEY_ALIAS} -PkeyPass=${KEY_PASSWORD} bundle${VARIANT}"
        }
      }
    }

    stage('Build APK') {
      steps {
        // Finish building and packaging the APK
        sh './gradlew assembleDebug'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'
      }
    }

    stage('Static analysis') {
      steps {
        // Run Lint and analyse the results
        script {
          sh './gradlew lintDebug'
        }
        androidLint pattern: '**/lint-results-*.xml'
      }
    }
    stage('Deploy') {
      when {
        // Only execute this stage when building from the `beta` branch
        branch 'master'
      }

      steps {
        // Build the app in release mode, and sign the APK using the environment variables
        sh './gradlew assembleRelease'

        // Archive the APKs so that they can be downloaded from Jenkins
        archiveArtifacts '**/*.apk'

        // Upload the APK to Google Play
       // androidApkUpload googleCredentialsId: 'Google Play', apkFilesPattern: '**/*-release.apk', trackName: 'beta'
      }
//      post {
//        success {
//          // Notify if the upload succeeded
//          mail to: 'andrew@avsoftware.co.uk', subject: 'New build available!', body: 'Check it out!'
//        }
//      }
    }
  }
//  post {
//    failure {
//      // Notify developer team of the failure
//      mail to: 'andrew@avsoftware.co.uk', subject: 'Oops!', body: "Build ${env.BUILD_NUMBER} failed; ${env.BUILD_URL}"
//    }
//  }
}