
# cna-shared-lib (Jenkins Shared Library, nivel medio)

Librería compartida de Jenkins orientada a **CI/CD** con utilidades para:
- Git y versionado semántico
- Build & push de imágenes Docker
- Deploy a Kubernetes (manifest/Helm)
- Calidad (cobertura, lints), artefactos, utilidades de notificación
- Wrappers de entorno (Node, Python, Docker host), checkout estándar, manejo de credenciales

> Pensada para usarse como **Global Pipeline Library**: `@Library('cna-lib@main') _`

## Estructura
```
vars/                    # Entradas públicas (steps / global vars)
src/org/cna/devops/     # Clases de negocio (no Steps)
resources/...            # Plantillas (libraryResource)
docs/                    # Guías y ejemplos
test/                    # Pruebas con Jenkins Pipeline Unit (Gradle)
```

## Requisitos
- En Jenkins: plugins `workflow-cps-global-lib`, `git`, `pipeline-utility-steps`, `credentials-binding`, etc.
- Agregar esta librería como **Global Pipeline Library** (JCasC o UI).

## Uso básico
```groovy
@Library('cna-lib@main') _

pipeline {
  agent any
  stages {
    stage('Build') {
      steps {
        dockerBuild image: 'cna/app', tag: gitInfo.shortSha()
      }
    }
    stage('Test & Quality') {
      steps {
        quality.junit('reports/junit/*.xml')
        quality.cobertura('reports/cobertura.xml')
      }
    }
    stage('Push') {
      steps {
        withDockerHost {
          dockerPush image: 'cna/app', tag: gitInfo.shortSha(), registry: 'ghcr.io/tu-org'
        }
      }
    }
    stage('Deploy') {
      steps {
        k8sDeploy.manifest(
          template: 'org/cna/devops/k8s/deployment.yaml',
          values: [ image: "ghcr.io/tu-org/cna/app:${gitInfo.shortSha()}", ns: 'demo' ]
        )
      }
    }
  }
  post {
    success { notify.slack("✅ OK ${env.JOB_NAME} #${env.BUILD_NUMBER}") }
    failure { notify.slack("❌ FAIL ${env.JOB_NAME} #${env.BUILD_NUMBER}") }
  }
}
```
# jenkins_sharedlib
