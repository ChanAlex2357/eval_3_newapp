# Upoad process in Frappe / ERPNext Api

Très bien ! Voici deux choses que tu demandes :

---

## ✅ 1. **URL d’upload standard de fichier dans Frappe / ERPNext**

Frappe/ERPNext expose un **endpoint REST d’upload natif**, utilisable sans créer de méthode personnalisée :

### 🔗 URL officielle

```
POST /api/method/upload_file
```

### 📋 Paramètres requis (multipart/form-data)

| Clé form-data | Type    | Description                          |
| ------------- | ------- | ------------------------------------ |
| `file`        | fichier | Le fichier à uploader                |
| `doctype`     | string  | (optionnel) Doctype auquel lier      |
| `docname`     | string  | (optionnel) Nom du document          |
| `is_private`  | string  | "1" ou "0", selon la confidentialité |

---

### ✅ Exemple avec `curl`

```bash
curl -X POST https://yourdomain/api/method/upload_file \
  -H "Authorization: token API_KEY:API_SECRET" \
  -F "file=@/chemin/vers/fichier.pdf" \
  -F "doctype=Employee" \
  -F "docname=HR-EMP-00001" \
  -F "is_private=1"
```

### ✅ Réponse

```json
{
  "message": {
    "file_url": "/private/files/fichier.pdf",
    "name": "fichier.pdf",
    ...
  }
}
```

---

## ✅ 2. **Créer ton propre endpoint API perso**

Tu veux créer un **endpoint custom qui prend un chemin `file_url` en paramètre**, puis **récupère les données** du fichier uploadé.

### 📍 Exemple : `/api/method/myapp.api.get_uploaded_file`

### 📦 Endpoint Python dans `myapp/api.py`

```python
import frappe

@frappe.whitelist()
def get_uploaded_file(file_url):
    """
    Retourne le contenu ou les infos d'un fichier déjà uploadé via /private/files/... ou /files/...
    """
    if not file_url:
        frappe.throw("file_url requis")

    # Retirer le préfixe URL pour obtenir le nom de fichier dans File
    file_name = file_url.replace("/private/files/", "").replace("/files/", "")
    file_doc = frappe.get_doc("File", {"file_name": file_name})

    # Lire le fichier depuis le disque (privé ou public)
    path = frappe.get_site_path("private", "files", file_name) if file_doc.is_private else frappe.get_site_path("public", "files", file_name)
    with open(path, "rb") as f:
        content = f.read()

    return {
        "file_name": file_doc.file_name,
        "is_private": file_doc.is_private,
        "attached_to_doctype": file_doc.attached_to_doctype,
        "attached_to_name": file_doc.attached_to_name,
        "size": len(content),
        "content_base64": frappe.safe_encode(content).decode("utf-8")
    }
```

---

### ✅ Exemple d'appel via `GET`

```http
GET /api/method/myapp.api.get_uploaded_file?file_url=/private/files/fichier.pdf
```

---

## 🛡️ Attention

* Ce genre d’endpoint **peut exposer des fichiers privés**, donc :

  * Limite l’accès via `frappe.has_permission`
  * Ou vérifie que le fichier est bien lié à un document visible par l’utilisateur

---

## ✅ Résumé

| Objectif                | URL / Méthode                                              |
| ----------------------- | ---------------------------------------------------------- |
| Upload standard         | `POST /api/method/upload_file`                             |
| Récupérer fichier perso | `GET /api/method/myapp.api.get_uploaded_file?file_url=...` |

Souhaites-tu que le contenu du fichier soit retourné en **stream HTTP pur** (comme un téléchargement `Content-Disposition: attachment`) au lieu de `base64` ?
