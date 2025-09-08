
package org.cna.devops

class Git implements Serializable {
  static String branch(script) {
    return (script.env.BRANCH_NAME ?: script.sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim())
  }
  static String commit(script) {
    return script.sh(script: "git rev-parse HEAD", returnStdout: true).trim()
  }
  static String shortSha(String full) { return full?.take(8) }
}
