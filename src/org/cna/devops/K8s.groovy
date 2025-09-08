
package org.cna.devops

class K8s implements Serializable {
  static void applyManifest(script, String content, String namespace=null) {
    def ns = namespace ? "-n ${namespace}" : ""
    script.writeFile file: 'render.yaml', text: content
    script.sh "kubectl apply -f render.yaml ${ns} || true"
  }
}
