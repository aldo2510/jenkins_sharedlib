/** Reusable Java/Maven pipeline. */
def call(Map args = [:]) {
  def repository = args.repository ?: error('standardJavaPipeline: repository es requerido')
  def branch = args.branch ?: 'main'
  def image = args.image ?: 'maven:3.9.3-eclipse-temurin-17'
  def goals = args.goals ?: 'clean package'

  pipeline {
    agent none
    stages {
      stage('Build') {
        agent { docker { image image } }
        stages {
          stage('Checkout') {
            steps { git branch: branch, url: repository }
          }
          stage('Version') {
            steps {
              script {
                def version = getPomVersion()
                currentBuild.displayName = "#${env.BUILD_NUMBER} - ${version}"
                currentBuild.description = "Version ${version}"
              }
            }
          }
          stage('Package') {
            steps { buildMaven goals: goals }
          }
          stage('Archive') {
            steps { archiveMavenArtifacts() }
          }
        }
      }
    }
  }
}
