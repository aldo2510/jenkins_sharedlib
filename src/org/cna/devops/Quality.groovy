
package org.cna.devops

class Quality implements Serializable {
  static void junit(script, String pattern) {
    script.junit allowEmptyResults: true, testResults: pattern
  }
  static void cobertura(script, String path) {
    try {
      script.cobertura coberturaReportFile: path, onlyStable: false, failNoReports: false
    } catch (Throwable t) {
      script.echo "Cobertura plugin no disponible: ${t.message}"
    }
  }
}
