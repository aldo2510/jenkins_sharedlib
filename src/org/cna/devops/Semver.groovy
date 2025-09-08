
package org.cna.devops

class Semver implements Serializable {
  static String nextPatch(String ver) {
    def m = (ver =~ /^(\d+)\.(\d+)\.(\d+)(.*)?$/)
    if (!m) return ver
    int p = (m[0][3] as int) + 1
    return "${m[0][1]}.${m[0][2]}.${p}${m[0][4]?:''}"
  }
}
