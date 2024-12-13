node('unix') {
    stage('Git checkout') {
        checkout scm
    }

    stage('Start test') {
        withMaven(globalMavenSettingsConfig: '', jdk: '', maven: 'Default', mavenSettingsConfig: '', traceability: true) {
            sh 'mvn clean test -Dtype.browser=${browser}'
        }
    }
    stage('Generate report') {
        allure includeProperties: false, jdk: '', results: [[path: 'target/reports/allure-results']]
    }
}