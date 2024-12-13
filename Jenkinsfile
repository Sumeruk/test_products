node('unix') {
    stage('Git checkout') {
        checkout scmGit(branches: [[name: '*/add_jenkins']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/Sumeruk/test_products.git']])
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