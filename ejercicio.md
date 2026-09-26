# Ejercicio: Jenkins Shared Library robusta para CI/CD Java

## Objetivo

Construir y consumir una Global Shared Library de Jenkins para centralizar lógica repetitiva de pipelines Java/Maven.

Repositorios:
- Shared Library: https://github.com/aldo2510/jenkins_sharedlib
- Aplicación ejemplo: https://github.com/aldo2510/ec-maven-users-api

## 1. Estructura

- `vars/`: pasos globales invocables desde el Jenkinsfile.
- `src/`: clases Groovy reutilizables.
- `resources/`: recursos para `libraryResource()`.
- `test/`: pruebas de Pipeline Unit.
- `examples/`: Jenkinsfiles de ejemplo.

Incluye:
- `buildMaven()`: Maven estandarizado.
- `getPomVersion()`: lee `project.version` desde `pom.xml`.
- `archiveMavenArtifacts()`: archiva JARs.
- `dockerBuildImage()`: build Docker.
- `addBuildTag()`: metadata del build.
- `standardJavaPipeline()`: workflow reutilizable.

## 2. Configurar Global Library en Jenkins

Ve a **Manage Jenkins → System → Global Pipeline Libraries** y agrega:

- **Name:** `aldo-jenkins-shared`
- **Default version:** `main`
- **Retrieval method:** Modern SCM
- **SCM:** Git
- **Project Repository:** `https://github.com/aldo2510/jenkins_sharedlib.git`

Si el repositorio es privado, configura las credenciales Git correspondientes.

### Recomendación de producción

En producción, publica versiones de la library usando tags de Git, por ejemplo `v1.0.0`, `v1.1.0` y `v2.0.0`. Así los equipos pueden fijar una versión estable y probar cambios de la library de forma controlada.

## 3. Consumir la library

En el Jenkinsfile:

```groovy
@Library('aldo-jenkins-shared') _
```

Después:

```groovy
def version = getPomVersion()
buildMaven goals: 'clean package'
archiveMavenArtifacts()
```

También puedes fijar explícitamente una versión:

```groovy
@Library('aldo-jenkins-shared@v1.0.0') _
```

## 4. Parte base: Maven Package

El pipeline original puede tener:

```groovy
pipeline {
  agent none
  stages {
    stage('build') {
      agent { docker { image 'maven:3.9.3-eclipse-temurin-17' } }
      steps {
        git branch: 'main', url: 'https://github.com/aldo2510/ec-maven-users-api.git'
        sh 'mvn -B clean package'
        archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
      }
    }
  }
}
```

La Shared Library convierte la lógica repetitiva en:

```groovy
buildMaven goals: 'clean package'
archiveMavenArtifacts artifacts: 'target/*.jar'
```

## 5. Leer versión desde pom.xml

Ejemplo:

```groovy
script {
  def version = getPomVersion()
  currentBuild.displayName = "#${env.BUILD_NUMBER} - ${version}"
}
```

La versión puede reutilizarse para:
- nombre del build;
- tags Docker;
- metadata;
- releases;
- observabilidad.

## 6. Pipeline de alto nivel

El ejemplo incluido en `examples/Jenkinsfile` queda así:

```groovy
@Library('aldo-jenkins-shared') _

standardJavaPipeline(
  repository: 'https://github.com/aldo2510/ec-maven-users-api.git',
  branch: 'main',
  dockerImage: 'maven:3.9.3-eclipse-temurin-17',
  goals: 'clean package'
)
```

Esto demuestra dos niveles de reutilización:

### Nivel 1: pasos reutilizables

```groovy
getPomVersion()
buildMaven()
archiveMavenArtifacts()
dockerBuildImage(...)
```

### Nivel 2: workflow reutilizable

```groovy
standardJavaPipeline(...)
```

## 7. Ejercicio práctico

### Parte A

Crea un Pipeline Job que:

1. Haga checkout de `main`.
2. Lea la versión de `pom.xml`.
3. Ejecute `mvn -B clean package`.
4. Archive `target/*.jar`.
5. Muestre la versión en el nombre del build.

### Parte B

Agrega metadata con:

```groovy
addBuildTag(["java", "maven", "version-${version}"])
```

### Parte C

Construye una imagen Docker:

```groovy
dockerBuildImage image: 'aldo/ec-maven-users-api', tag: version
```

### Parte D — reto

Modifica `standardJavaPipeline.groovy` para aceptar parámetros adicionales, por ejemplo:

```groovy
standardJavaPipeline(
  repository: '...',
  branch: 'main',
  dockerImage: 'maven:3.9.3-eclipse-temurin-17',
  goals: 'clean verify'
)
```

Haz que el workflow permita decidir si ejecuta tests, si archiva artefactos y qué imagen Docker utiliza.

## 8. Reto de calidad

Agrega funciones como:

```groovy
runMavenTests()
runMavenVerify()
publishTestResults()
publishCoverage()
```

Integra después las etapas:

```text
Checkout
Version
Test
Package
Archive
```

## 9. Reto DevSecOps

Centraliza wrappers para herramientas como:

```text
runSonar()
runTrivy()
runOWASPZAP()
publishTestResults()
publishCoverage()
```

El Jenkinsfile debe declarar principalmente la intención y los parámetros del proyecto; la implementación común debe permanecer en la library.

## 10. Reto de diseño de plataforma

Crea una segunda función de alto nivel orientada a microservicios, por ejemplo:

```groovy
microserviceJavaPipeline(
  repository: '...',
  serviceName: 'users-api',
  dockerImage: 'aldo/users-api'
)
```

El objetivo es que esta función pueda combinar:

```text
Checkout
→ Version
→ Unit Tests
→ Maven Package
→ Security
→ Docker Build
→ Archive
```

## 11. Resultado esperado

```text
jenkins_sharedlib/
├── vars/
│   ├── addBuildTag.groovy
│   ├── archiveMavenArtifacts.groovy
│   ├── buildMaven.groovy
│   ├── dockerBuildImage.groovy
│   ├── getPomVersion.groovy
│   └── standardJavaPipeline.groovy
├── src/
│   └── org/aldo/jenkins/
├── resources/
│   └── org/aldo/jenkins/
├── test/
├── examples/
└── ejercicio.md
```

La idea final es separar **estándares de plataforma**, **lógica reutilizable** y **configuración específica de la aplicación**.
