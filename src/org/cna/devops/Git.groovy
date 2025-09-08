package org.acme.devops

class Git implements Serializable {
  static String branch(script) {
    if (script.env.BRANCH_NAME) return script.env.BRANCH_NAME
    if (script.env.GIT_BRANCH)  return script.env.GIT_BRANCH
    return script.sh(script: "git rev-parse --abbrev-ref HEAD", returnStdout: true).trim()
  }
  static String commit(script) {
    if (script.env.GIT_COMMIT) return script.env.GIT_COMMIT
    return script.sh(script: "git rev-parse HEAD", returnStdout: true).trim()
  }
  static String shortSha(String full) { full?.take(8) }
}
