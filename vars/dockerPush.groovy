
import org.cna.devops.Docker

/**
 * dockerPush image: 'cna/app', tag: '1234abcd', registry: 'ghcr.io/cna'
 */
def call(Map args = [:]) {
  def image = args.image ?: error('dockerPush: image es requerido')
  def tag = args.tag ?: error('dockerPush: tag es requerido')
  def registry = args.registry
  Docker.push(this, image, tag, registry)
}
