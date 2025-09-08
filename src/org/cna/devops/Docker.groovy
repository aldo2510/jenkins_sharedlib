
package org.cna.devops

class Docker implements Serializable {
  static void build(script, String image, String tag='latest', String dockerfile='Dockerfile', String context='.') {
    script.sh "docker build -f ${dockerfile} -t ${image}:${tag} ${context}"
  }
  static void push(script, String image, String tag, String registry=null) {
    def ref = registry ? "${registry}/${image}" : image
    script.sh "docker tag ${image}:${tag} ${ref}:${tag}"
    script.sh "docker push ${ref}:${tag}"
  }
}
