
# Estilo y convenciones

- **Código público** en `vars/` con funciones pequeñas y documentadas.
- **Lógica** en `src/` (sin pasos de Jenkins). Usa clases `Serializable` si necesitas estado.
- Usa **GStrings** para interpolación y comillas simples para literales.
- Evita `sh 'set -x'` con secretos. Usa `withCredentials`.
- Prefiere `returnStdout: true` y `trim()` cuando tomes salidas de `sh`.
