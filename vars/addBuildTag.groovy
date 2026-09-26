/** Adds one or multiple build labels to display metadata. */
def call(tags) {
  def values = tags instanceof Collection ? tags : [tags]
  values.findAll { it != null && it.toString().trim() }.each { tag ->
    echo "Build tag: ${tag}"
    currentBuild.description = ((currentBuild.description ?: '') + ' ' + tag).trim()
  }
}
