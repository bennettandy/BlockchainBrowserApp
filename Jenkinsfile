pipeline {
  agent {
    // Run on a build agent where we have the Android SDK installed
    docker { image "avsoftware/android-agent:latest"}
  }
  environment {
    GOOGLE_API_KEY = credentials('jenkins-google-api-key')
    GOOGLE_SERVICES_JSON = credentials('google-services-json')
  }
  stages{
    stage('Emulator'){
      steps{
        echo "Check installed packages"
        //sh 'sdkmanager --list'
        echo "List Emulators"
        //sh 'avdmanager list avd'
        echo "Create Emulator"
        //sh 'echo no | avdmanager create avd -n first_avd --abi google_apis/x86_64 -k "system-images;android-30;google_apis;x86_64"'
        echo "Start Emulator"
        //sh 'emulator -avd first_avd -gpu swiftshader_indirect -no-window -no-audio &'
        //echo "Run Emulator"
        //sh 'docker run --publish 8554:8554/tcp --publish 5554:5554/tcp --publish 5555:5555/tcp us-docker.pkg.dev/android-emulator-268719/images/28-playstore-x64:30.1.2'
      }
    }
//    stage('Test'){
//      steps {
//        sh './gradlew test'
//      }
//    }
    stage('Build'){
      steps{
        //sh 'rm -rf /var/lib/jenkins/workspace/kotlin_android_pipeline/app/build/test-results/testReleaseUnitTest/TEST-com.yodle.android.kotlindemo.service.GitHubApiServiceTest.xml'
        sh './gradlew clean test build'
      }
    }
    stage('Sonar'){
      steps{
        //sh 'rm -rf /var/lib/jenkins/workspace/kotlin_android_pipeline/app/build/test-results/testReleaseUnitTest/TEST-com.yodle.android.kotlindemo.service.GitHubApiServiceTest.xml'
        sh './gradlew sonarqube'
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