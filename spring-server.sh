#!/bin/bash

# === Configuration mémoire JVM ===
JVM_MIN_HEAP="256m"
JVM_MAX_HEAP="512m"

# === Port du débogueur (par défaut : 5005) ===
DEBUG_PORT=5005

# === Configuration des options JVM ===
export MAVEN_OPTS="-Xms$JVM_MIN_HEAP -Xmx$JVM_MAX_HEAP \
-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=*:$DEBUG_PORT"


# === Affichage des infos système ===
echo "➡️  Lancement de l'application Spring Boot avec debug actif :"
echo "🧠  Mémoire JVM : -Xms$JVM_MIN_HEAP -Xmx$JVM_MAX_HEAP"
echo "🐞  Debug à distance activé sur le port : $DEBUG_PORT"
echo "------------------------------------"
echo "📦  Démarrage via Maven..."
echo ""

# === Lancement de l'application ===
mvn spring-boot:run

# === Fin ===
echo ""
echo "🛑  Application arrêtée."

