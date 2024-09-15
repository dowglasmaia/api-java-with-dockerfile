#!/bin/bash

# Configurações
SONAR_HOST_URL="http://localhost:9000"
SONAR_PROJECT_KEY="api-docker-sonar"
SONAR_TOKEN="seu_token_sonar"

# Consulta o status do Quality Gate
STATUS=$(curl -s -u "$SONAR_TOKEN:" "$SONAR_HOST_URL/api/qualitygates/project_status?projectKey=$SONAR_PROJECT_KEY" | jq -r '.projectStatus.status')

echo "Quality Gate status: $STATUS"

if [ "$STATUS" != "OK" ]; then
  echo "Quality Gate falhou. Abortando o build."
  exit 1
else
  echo "Quality Gate passou."
fi
