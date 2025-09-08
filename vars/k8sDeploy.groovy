
import org.cna.devops.K8s

/**
 * k8sDeploy.manifest template:'org/cna/devops/k8s/deployment.yaml', values:[image:'cna/app:1.0.0', ns:'demo']
 */
def manifest(Map args = [:]) {
  def tpl = args.template ?: error('k8sDeploy.manifest: template requerido')
  def values = args.values ?: [:]
  def raw = libraryResource(tpl)
  String rendered = raw
  values.each { k,v -> rendered = rendered.replace('${'+k+'}', String.valueOf(v)) }
  K8s.applyManifest(this, rendered, values.ns as String)
}
