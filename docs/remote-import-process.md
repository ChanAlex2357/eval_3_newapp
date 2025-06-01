# Remote Import

Excellente approche — et tu as déjà bien cerné les meilleures pratiques **d'intégration Frappe ↔ Spring Boot**. Voici une proposition structurée pour **reproduire ton flux d'import personnalisé** avec une architecture hybride propre, souple et maintenable :

---

## ✅ Objectif : Importer des employés + salaires **depuis Spring Boot** vers Frappe

### 🔄 Architecture souhaitée

| Étape                        | Spring Boot (Frontend/Backend)                          | Frappe                                                                                               |
| ---------------------------- | ------------------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| 1. Upload fichiers CSV/Excel | Formulaire d'import (multi-fichier)                     | —                                                                                                    |
| 2. Envoi vers Frappe         | Appel API REST Frappe (multipart ou JSON base64)        | Réception des fichiers via `Custom Import Doctype`                                                   |
| 3. Lancement du traitement   | Appel à une méthode Frappe custom `@frappe.whitelist()` | Lecture, parsing, insertion dans `Employee`, `Salary Structure`, `Salary Structure Assignment`, etc. |
| 4. Retour du résultat        | Affichage résumé succès/erreurs dans Spring Boot        | JSON API avec feedback (log + erreurs ligne par ligne)                                               |

---

## 📦 Côté Frappe : structure recommandée

### 1. Créer un `Doctype` custom : `Remote Import Task`

Contient :

* Statut (`Draft`, `In Progress`, `Success`, `Failed`)
* Fichier 1 : `Employee File`
* Fichier 2 : `Salary Assignment File`
* Logs : champ longtext HTML
* Champs divers : ID externe, utilisateur source, etc.

```python
# remote_import_task.py
@frappe.whitelist()
def process_remote_import(name):
    task = frappe.get_doc("Remote Import Task", name)
    # lire fichiers
    # parser avec pandas ou csv.reader
    # valider lignes
    # insérer dans Employee, Salary Structure Assignment, etc.
    # journaliser
    # mettre à jour le statut
```

---

## 🌐 Côté Spring Boot : étapes

### 1. Formulaire avec upload multiple

* Validation en amont (extension, format)
* Envoi POST vers Frappe avec :

  * Fichiers en `multipart/form-data` **ou**
  * Fichiers en base64 dans JSON si tu veux un appel plus structuré

### 2. Création d’un `Remote Import Task` via l’API

```http
POST /api/resource/Remote Import Task
{
  "employee_file": "data:application/vnd.ms-excel;base64,...",
  "salary_file": "data:application/vnd.ms-excel;base64,..."
}
```

Tu peux aussi :

* Créer le doctype d’abord (`POST` sans les fichiers)
* Puis envoyer les fichiers un par un via `/api/resource/Remote Import Task/<name>`

### 3. Appel de la méthode de traitement

```http
POST /api/method/eval_app.remote_import_task.process_remote_import
{
  "name": "RIMPT-00001"
}
```

---

## ✨ Bonus : traitement asynchrone

Tu peux aussi lancer la méthode de traitement dans un `frappe.enqueue()` (background job) pour éviter le timeout côté Spring.

```python
@frappe.whitelist()
def launch_import(name):
    frappe.enqueue("eval_app.remote_import_task.process_remote_import", name=name)
```

---

## 📊 Résultat à retourner à Spring Boot

Fais en sorte que ta méthode retourne un JSON propre :

```json
{
  "status": "success",
  "inserted": 23,
  "errors": [
    {"line": 8, "error": "Invalid email"},
    {"line": 12, "error": "Salary missing"}
  ]
}
```

---

## 🧩 En résumé

| Élément               | À faire                                         |
| --------------------- | ----------------------------------------------- |
| Frappe Doctype        | `Remote Import Task` avec fichiers et logs      |
| Méthode de traitement | `frappe.whitelist()` pour lire, parser, insérer |
| Spring Boot           | Form upload + appel REST vers Frappe            |
| Résultat              | JSON de statut + log d’erreurs                  |

---

Souhaites-tu que je t’écrive :

* Le `Doctype JSON` pour `Remote Import Task` ?
* Ou le code `@frappe.whitelist()` pour parser et insérer les employés ?
* Ou bien le `Service REST` Spring Boot pour uploader et piloter l'import ?
