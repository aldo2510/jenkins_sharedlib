/** Reads project.version from pom.xml without requiring Maven. */
def call(String pom = 'pom.xml') {
  if (!fileExists(pom)) error "getPomVersion: no existe ${pom}"
  def content = readFile(pom)
  def matcher = content =~ /<version>\s*([^<]+)\s*<\/version>/
  if (!matcher.find()) error "getPomVersion: no se encontró <version> en ${pom}"
  def version = matcher.group(1).trim()
  echo "POM version: ${version}"
  return version
}
