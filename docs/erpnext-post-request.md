# Post Request

Dans **ERPNext / Frappe**, pour un **endpoint API personnalisé** (défini via `@frappe.whitelist()`), tu peux récupérer les données d'une requête **POST** comme suit :

---

## ✅ 1. Récupérer les données `JSON` du body POST

### 🎯 Utilise `frappe.request.get_json()`

```python
import frappe
from frappe import _

@frappe.whitelist(allow_guest=False)
def my_api_handler():
    data = frappe.request.get_json()

    # Exemple : récupérer un champ
    company = data.get("company")
    employee = data.get("employee")
    
    return {"message": f"Reçu: {company}, {employee}"}
```

---

## ✅ 2. Exemple d’appel côté client

### 🔗 Requête `POST` via `curl`

```bash
curl -X POST https://yourdomain/api/method/path.to.module.my_api_handler \
  -H "Authorization: token API_KEY:API_SECRET" \
  -H "Content-Type: application/json" \
  -d '{"company": "My Company", "employee": "HR-EMP-00001"}'
```

---

## ✅ 3. Structure de réponse Frappe

Frappe renverra toujours un JSON enveloppé comme ceci :

```json
{
  "message": {
    "message": "Reçu: My Company, HR-EMP-00001"
  }
}
```

---

## ✅ Autres cas

### 📎 Si les données viennent de `form-data` (type `multipart/form-data` ou `x-www-form-urlencoded`)

Utilise :

```python
company = frappe.form_dict.get("company")
```

Mais **ceci est rarement utilisé** en API REST — préfère `get_json()` pour les payloads `application/json`.

---

## ✅ Résumé

| Cas                              | Méthode                     |
| -------------------------------- | --------------------------- |
| Requête POST JSON                | `frappe.request.get_json()` |
| Formulaire (form data classique) | `frappe.form_dict.get(...)` |

---

Souhaites-tu un exemple complet de déclaration d'API dans `hooks.py` + endpoint + test `curl` ou `fetch()` ?
