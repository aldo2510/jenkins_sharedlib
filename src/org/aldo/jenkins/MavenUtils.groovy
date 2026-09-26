package org.aldo.jenkins

class MavenUtils implements Serializable {
  private static final long serialVersionUID = 1L

  static String coordinates(String groupId, String artifactId, String version) {
    return "${groupId}:${artifactId}:${version}"
  }
}
