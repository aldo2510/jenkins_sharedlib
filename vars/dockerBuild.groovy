
import org.cna.devops.Docker

/**
 * dockerBuild image: 'cna/app', tag: '1234abcd', dockerfile: 'Dockerfile', context: '.'
 */
def call(Map args = [:]) {
  def image = args.image ?: error('dockerBuild: image es requerido')
  def tag = args.tag ?: 'latest'
  def dockerfile = args.dockerfile ?: 'Dockerfile'
  def context = args.context ?: '.'
  Docker.build(this, image, tag, dockerfile, context)
}
