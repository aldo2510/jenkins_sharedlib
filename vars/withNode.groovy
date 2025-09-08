
/**
 * withNode('18'){ ... }  // asume imagen/agent con node instalado
 */
def call(String version = '18', Closure body) {
  echo "Usando Node ${version} (wrapper simbólico)"
  body()
}
