# Parent Child Request dans Frappe

Pour faire une requête sur **les champs enfants (child tables)** d’un **DocType parent** comme `Salary Slip`, et plus précisément sur les champs `earnings` ou `deductions` (qui sont des listes de `Salary Detail`), voici les différentes manières **dans Frappe** :

---

## ✅ 1. **Depuis le parent avec `frappe.get_all` ne fonctionne pas directement**

Tu ne peux pas **filtrer directement** dans `frappe.get_all("Salary Slip")` sur les child tables. Tu dois interroger le **DocType enfant directement**.

---

## ✅ 2. **Requête directe sur `Salary Detail`**

Chaque ligne de `earnings` ou `deductions` est stockée dans la **table `tabSalary Detail`**, avec des colonnes :

* `parent` → le nom du `Salary Slip`
* `parenttype` = `"Salary Slip"`
* `parentfield` = `"earnings"` ou `"deductions"`

### ▶ Exemple : Tous les `Salary Detail` de type `earning` sur un mois donné

```python
from frappe.utils import getdate

frappe.get_all(
    "Salary Detail",
    filters={
        "parenttype": "Salary Slip",
        "parentfield": "earnings",
        "salary_component": "Salaire de Base",
        "creation": ["between", [getdate("2025-07-01"), getdate("2025-07-31")]]
    },
    fields=["parent", "salary_component", "amount"]
)
```

---

## ✅ 3. **Pour filtrer sur `Salary Slip` ET les `earnings`, utiliser SQL ou `frappe.db.sql`**

### ▶ Exemple : SQL query pour obtenir les salaires avec un composant spécifique

```python
frappe.db.sql("""
    SELECT 
        ss.name AS salary_slip_name,
        sd.salary_component,
        sd.amount
    FROM 
        `tabSalary Slip` ss
    JOIN 
        `tabSalary Detail` sd 
        ON sd.parent = ss.name
    WHERE 
        sd.parenttype = 'Salary Slip'
        AND sd.parentfield = 'earnings'
        AND sd.salary_component = %s
        AND ss.posting_date BETWEEN %s AND %s
""", ("Salaire de Base", "2025-07-01", "2025-07-31"), as_dict=True)
```

---

## ✅ 4. **Avec REST API**

Requête via `/api/resource/Salary Detail?filters=...`

```http
GET /api/resource/Salary Detail?filters=[
    ["parenttype","=","Salary Slip"],
    ["parentfield","=","earnings"],
    ["salary_component","=","Salaire de Base"]
]
```

---

### Résumé

| Objectif                          | Méthode                             | Remarques                                  |
| --------------------------------- | ----------------------------------- | ------------------------------------------ |
| Filtrer `Salary Slip` par earning | ❌ Impossible directement via parent | Il faut passer par `Salary Detail`         |
| Filtrer `Salary Detail`           | ✅ `frappe.get_all("Salary Detail")` | utiliser `parentfield`, `parenttype`, etc. |
| Liaison parent-enfant + filtre    | ✅ `frappe.db.sql`                   | SQL est plus souple pour les jointures     |

Souhaites-tu une fonction Java/Thymeleaf ou un service Java Spring qui exploite cette logique ?
