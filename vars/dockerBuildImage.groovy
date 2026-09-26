/** Builds a Docker image with standardized arguments. */
def call(Map args = [:]) {
  def image = args.image ?: error('dockerBuildImage: image es requerido')
  def tag = args.tag ?: 'latest'
  def dockerfile = args.dockerfile ?: 'Dockerfile'
  def context = args.context ?: '.'
  sh "docker build -f '${dockerfile}' -t '${image}:${tag}' '${context}'"
}
