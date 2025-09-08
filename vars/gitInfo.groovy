
import org.cna.devops.Git

/**
 * gitInfo.shortSha() / gitInfo.branch() / gitInfo.summary()
 */
def shortSha() { Git.shortSha(full()) }
def branch() { Git.branch(this) }
def full() { Git.commit(this) }
def summary() {
  def f = full()
  return [ branch: branch(), full: f, short: Git.shortSha(f) ]
}
