import org.cna.devops.Git
def shortSha() { Git.shortSha(full()) }
def branch() { Git.branch(this) }
def full() { Git.commit(this) }
def summary() {
  def f = full()
  [ branch: branch(), full: f, short: Git.shortSha(f) ]
}
