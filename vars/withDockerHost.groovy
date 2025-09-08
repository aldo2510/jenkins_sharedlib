
/**
 * withDockerHost { ... }  // asegura docker disponible
 */
def call(Closure body) {
  try {
    sh 'docker version || true'
    body()
  } finally {
    echo 'Fin withDockerHost'
  }
}
