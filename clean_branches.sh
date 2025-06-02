#!/bin/bash

echo "Mise à jour des informations distantes..."
git fetch -p

echo
echo "Recherche des branches locales orphelines..."

# Lister les branches locales avec détails, filtrer celles marquées 'gone'
for branch in $(git branch -vv | grep ": gone]" | awk '{print $1}'); do
    echo "Suppression de la branche locale orpheline: $branch"
    git branch -D "$branch"
done

echo
echo "Nettoyage terminé."
read -p "Appuyez sur Entrée pour quitter..."
