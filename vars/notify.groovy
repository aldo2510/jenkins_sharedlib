
/**
 * Notificaciones (simples). En prod, integra Slack/Email APIs.
 */
def slack(String msg, String channel = '#general') {
  echo "[SLACK:${channel}] ${msg}"
}
def email(String to, String subject, String body) {
  echo "[EMAIL:${to}] ${subject}\n${body}"
}
