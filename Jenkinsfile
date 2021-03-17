pipeline {
  agent {
    // Run on a build agent where we have the Android SDK installed
    docker { image "androidsdk/android-30:latest"}
  }

  stages{
    stage('Emulator'){
      steps{
        echo "Pull Emulator Docker Image"
        sh 'docker pull us-docker.pkg.dev/android-emulator-268719/images/28-playstore-x64:30.1.2'
        echo "Run Emulator"
        sh 'docker run --publish 8554:8554/tcp --publish 5554:5554/tcp --publish 5555:5555/tcp us-docker.pkg.dev/android-emulator-268719/images/28-playstore-x64:30.1.2'
      }
    }
    stage('Build'){
      steps{
        //sh 'rm -rf /var/lib/jenkins/workspace/kotlin_android_pipeline/app/build/test-results/testReleaseUnitTest/TEST-com.yodle.android.kotlindemo.service.GitHubApiServiceTest.xml'
        sh './gradlew clean test build'
      }
    }
    stage('Reports'){
      steps{
        // Run Lint and analyse the results
        sh './gradlew lintDebug'
        junit '**/build/test-results/testReleaseUnitTest/*.xml'

        recordIssues(
                enabledForFailure: true, aggregatingResults: true,
                tools: [java(), checkStyle(pattern: 'lint-results*.xml', reportEncoding: 'UTF-8')]
        )
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