/** Runs Maven with consistent defaults. */
def call(Map args = [:]) {
  def mavenCmd = args.mavenCmd ?: 'mvn'
  def goals = args.goals ?: 'clean package'
  def options = args.options ?: '-B -ntp'
  sh "${mavenCmd} ${options} ${goals}"
}
