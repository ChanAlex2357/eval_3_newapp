#!/bin/bash

# === Configuration mémoire JVM ===
JVM_MIN_HEAP="256m"
JVM_MAX_HEAP="512m"

# === Configuration des options JVM ===
export MAVEN_OPTS="-Xms$JVM_MIN_HEAP -Xmx$JVM_MAX_HEAP"

# === Affichage des infos système ===
echo "➡️  Lancement de l'application Spring Boot avec les paramètres suivants :"
echo "🧠  Mémoire JVM : -Xms$JVM_MIN_HEAP -Xmx$JVM_MAX_HEAP"
echo "🛠  MAVEN_OPTS : $MAVEN_OPTS"
echo "------------------------------------"
echo "📦  Démarrage via Maven..."
echo ""

# === Lancement de l'application ===
mvn spring-boot:run

# === Fin ===
echo ""
echo "🛑  Application arrêtée."
