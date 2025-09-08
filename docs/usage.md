
# Guía de uso rápido

## Registrar la librería
- **Nombre**: `cna-lib`
- **Retriever**: Modern SCM (Git)
- **Repo**: URL de esta librería
- **Versión por defecto**: `main` (o la que uses)

## Ejemplos de steps
```groovy
hello('Dev')
def info = gitInfo.summary()
echo "Branch: ${info.branch} SHA: ${info.short}"

withNode('18') {
  sh 'node --version'
}

withPython('3.11') {
  sh 'python --version'
}

dockerBuild image: 'cna/app', tag: '1.0.0'
withDockerHost { dockerPush image: 'cna/app', tag: '1.0.0', registry: 'ghcr.io/cna' }

k8sDeploy.manifest(
  template: 'org/cna/devops/k8s/deployment.yaml',
  values: [ image: 'ghcr.io/cna/app:1.0.0', ns: 'demo' ]
)
```
