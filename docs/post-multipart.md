# Post Multipart

Très bonne question ! La réponse est **non** : pour les **fichiers `multipart/form-data`** dans un endpoint Frappe (ERPNext), **ce n’est pas `get_json()`** qu’il faut utiliser.

---

## ✅ Pour récupérer des **fichiers et champs `multipart/form-data`**, tu dois utiliser

```python
frappe.request.files  # pour les fichiers (type Werkzeug FileStorage)
frappe.form_dict      # pour les champs texte
```

---

### 📂 Exemple d’upload de fichier + texte

#### 🔗 Requête côté client (ex: `curl`)

```bash
curl -X POST https://yourdomain/api/method/myapp.api.upload_file \
  -H "Authorization: token API_KEY:API_SECRET" \
  -F "docname=HR-EMP-00001" \
  -F "upload_file=@/path/to/file.pdf"
```

---

### ✅ Endpoint Python dans Frappe

```python
import frappe
from frappe.utils.file_manager import save_file
from frappe import _

@frappe.whitelist(allow_guest=False)
def upload_file():
    # champs texte (multipart)
    docname = frappe.form_dict.get("docname")

    # fichier (clé = "upload_file")
    uploaded_file = frappe.request.files.get("upload_file")

    if not uploaded_file:
        frappe.throw(_("Aucun fichier trouvé"))

    # enregistrer le fichier (optionnel : lié à un DocType)
    saved_file = save_file(
        fname=uploaded_file.filename,
        content=uploaded_file.stream.read(),
        dt="Employee",
        dn=docname,
        folder=None,
        is_private=1
    )

    return {"file_url": saved_file.file_url}
```

---

## 🔍 Résumé : ce qu’il faut utiliser

| Type de donnée              | Méthode à utiliser              |
| --------------------------- | ------------------------------- |
| JSON                        | `frappe.request.get_json()`     |
| Form data (clé/valeur)      | `frappe.form_dict.get(...)`     |
| Fichiers (multipart upload) | `frappe.request.files.get(...)` |

---

Souhaites-tu :

* que je t’écrive un exemple pour envoyer **plusieurs fichiers** ?
* ou que je te montre comment **lier le fichier à un autre doctype personnalisé** ?
