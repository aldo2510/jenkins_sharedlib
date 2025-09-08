
apiVersion: v2
name: demo-chart
version: 0.1.0
---
# templates/deployment.yaml (ejemplo)
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ .Values.name }}
spec:
  replicas: {{ .Values.replicas }}
  template:
    metadata:
      labels: { app: {{ .Values.name }} }
    spec:
      containers:
      - name: {{ .Values.name }}
        image: {{ .Values.image }}
