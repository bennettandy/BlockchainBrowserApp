pipeline {
  agent {
    // Run on a build agent where we have the Android SDK installed
    docker { image "androidsdk/android-30:latest"}
  }

  stages{
    stage('Extract Source Code'){
      steps{
        git 'https://github.com/ctproject4/kotlin_android.git'
      }
    }
    stage('Build'){
      steps{
        sh 'rm -rf /var/lib/jenkins/workspace/kotlin_android_pipeline/app/build/test-results/testReleaseUnitTest/TEST-com.yodle.android.kotlindemo.service.GitHubApiServiceTest.xml'
        sh './gradlew clean test build'
      }
    }
    stage('Reports'){
      steps{
        // Run Lint and analyse the results
        sh './gradlew lintDebug'
        androidLintParser pattern: '**/lint-results-*.xml'
        junit '**/build/test-results/testReleaseUnitTest/*.xml'
      }
    }
    stage('Deploy'){
      steps {
        echo "DEPLOY ME"
      }
//      steps([$class : hockyapp.HockeyappRecorder]){
//        hockeyApp applications: [[apiToken: 'd17953a8b21940d08b5d09326d842d15', downloadAllowed: true, filePath: '**/*.apk', releaseNotesMethod: none(), uploadMethod: appCreation(true)]],baseUrlHolder: [baseUrl: 'https://rink.hockeyapp.net'], debugMode: false, failGracefully: false
//      }
    }
  }
}