# Jenkins Shared Library — CI/CD y DevSecOps

Shared Library de Jenkins para centralizar prácticas reutilizables de CI/CD y reducir lógica duplicada en Jenkinsfiles.

Repositorio: https://github.com/aldo2510/jenkins_sharedlib

## Componentes principales

### Java / Maven
- `buildMaven()`: ejecuta Maven con defaults consistentes (`-B -ntp`).
- `getPomVersion()`: obtiene la versión desde `pom.xml` sin depender de Maven.
- `archiveMavenArtifacts()`: estandariza el archivado de JARs.
- `standardJavaPipeline()`: workflow completo Checkout → Version → Package → Archive.

### Contenedores
- `dockerBuildImage()`: wrapper para construir imágenes Docker.
- `dockerBuild()` y `dockerPush()`: utilidades existentes para Docker.

### Plataforma / DevSecOps
La librería existente también incluye utilidades para Git, Kubernetes, calidad, notificaciones y wrappers de entorno. La idea es mantener en la library la implementación común y en los Jenkinsfiles solamente la configuración específica de cada aplicación.

## Estructura

```text
vars/                    # Steps públicos
src/org/aldo/jenkins/    # Clases Groovy reutilizables
resources/               # Recursos usados mediante libraryResource()
test/                    # Pruebas de Pipeline Unit
examples/                # Jenkinsfiles de ejemplo
docs/                    # Documentación
```

## Configuración como Global Pipeline Library

En Jenkins: **Manage Jenkins → System → Global Pipeline Libraries**.

- **Name:** `aldo-jenkins-shared`
- **Default version:** `main`
- **Retrieval method:** Modern SCM
- **SCM:** Git
- **Repository:** `https://github.com/aldo2510/jenkins_sharedlib.git`

Para producción se recomienda consumir versiones etiquetadas, por ejemplo:

```groovy
@Library('aldo-jenkins-shared@v1.0.0') _
```

## Uso básico

```groovy
@Library('aldo-jenkins-shared') _

def version = getPomVersion()
buildMaven goals: 'clean package'
archiveMavenArtifacts artifacts: 'target/*.jar'
```

## Pipeline de ejemplo

```groovy
@Library('aldo-jenkins-shared') _

standardJavaPipeline(
  repository: 'https://github.com/aldo2510/ec-maven-users-api.git',
  branch: 'main',
  image: 'maven:3.9.3-eclipse-temurin-17',
  goals: 'clean package'
)
```

Consulta `ejercicio.md` para el laboratorio paso a paso y los retos de extensión.
