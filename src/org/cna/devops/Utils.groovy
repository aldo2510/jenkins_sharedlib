
package org.cna.devops

class Utils implements Serializable {
  static String shout(String s) { s?.toUpperCase() }
  static String shellEscape(String s) { s.replace("'", "'"'"'") }
}
