/** Archives Maven build artifacts. */
def call(Map args = [:]) {
  archiveArtifacts artifacts: (args.artifacts ?: 'target/*.jar'),
    allowEmptyArchive: (args.containsKey('allowEmpty') ? args.allowEmpty : false),
    fingerprint: (args.containsKey('fingerprint') ? args.fingerprint : true),
    followSymlinks: false
}
