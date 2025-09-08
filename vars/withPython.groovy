
/**
 * withPython('3.11'){ ... } // wrapper simbólico
 */
def call(String version = '3.11', Closure body) {
  echo "Usando Python ${version} (wrapper simbólico)"
  body()
}
