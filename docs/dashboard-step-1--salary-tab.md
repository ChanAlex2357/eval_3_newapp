Parfait, voici la **fonction Frappe complète** `get_salary_stats`, que tu peux appeler via :

```
POST /api/method/eval_app.api.dashboard.get_salary_stats?year=2025
```

---

### ✅ Elle retourne :

```json
{
  "months": [
    {
      "month": "2025-01",
      "total_net": 12000,
      "total_gross": 14000,
      "components": {
        "Basic": 10000,
        "Indemnité": 4000,
        "Taxe sociale": -2000
      }
    },
    ...
  ]
}
```

---

### ✅ Étapes suivantes possibles :

1. 🔧 Générer le modèle Java Spring Boot `SalaryStatsResponse`
2. 📊 Créer la page HTML avec le tableau de résumé + lien vers le détail
3. 📈 Intégrer Chart.js pour afficher l'évolution mensuelle des salaires

Souhaites-tu maintenant le modèle Java et le `fetch` correspondant dans Spring ?
