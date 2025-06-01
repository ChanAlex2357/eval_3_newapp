# Salary workflow

Parfait, si tu accèdes déjà aux fiches d'employés via ton application Spring Boot connectée à Frappe/ERPNext HRMS, tu es bien avancé !

---

## 🎯 Objectif

**Afficher les informations salariales d’un employé** (dans sa fiche) à partir du système HRMS de Frappe.

---

## 📦 Composants liés à la gestion des salaires dans HRMS (ERPNext)

Frappe HRMS (anciennement ERPNext HR) gère le salaire via plusieurs **DocTypes liés**, chacun avec un rôle bien précis :

---

### 🧱 1. `Salary Structure`

* 📌 C’est le modèle de rémunération d’un employé.
* Il définit **les composantes du salaire** (salaire de base, allocations, déductions...).
* Peut être lié à un ou plusieurs employés.

#### Champs clés

* `employee`
* `base`
* `earnings` (table)
* `deductions` (table)
* `is_active`

---

### 🧾 2. `Salary Slip`

* 📌 C’est la **fiche de paie mensuelle générée à partir d’un Salary Structure**.
* Elle contient le **salaire net**, les composantes exactes pour la période, les heures, les absences, etc.
* C’est le document "final" pour le salaire d’un employé à une date donnée.

#### Champs clés

* `employee`
* `start_date` / `end_date`
* `earnings`
* `deductions`
* `gross_pay`
* `net_pay`
* `status` (Draft / Submitted / Cancelled)

---

### 📋 3. `Salary Component`

* 📌 Ce sont les **éléments de paie de base** (e.g. "Basic", "HRA", "Transport", "Tax", etc.)
* Ils sont utilisés dans les `Salary Structure` et `Salary Slip`.

---

## 🔁 Comment l’HRMS gère le salaire

Voici un résumé du **workflow** :

1. **Création d’un `Salary Structure`** pour l’employé.
2. Si activé, le `Salary Structure Assignment` l’assigne à une période donnée.
3. Chaque mois, un **`Salary Slip`** est généré manuellement ou automatiquement via une tâche ou une API.
4. Le `Salary Slip` peut être soumis.
5. Ces fiches sont utilisées pour les rapports, la paie, les virements bancaires, etc.

---

## 🧠 Cas d’utilisation dans ton application

### 🔍 Pour **voir le salaire actuel d’un employé**

#### 🔹 Option 1 : Dernier `Salary Slip`

Tu peux appeler l’API pour lister les `Salary Slip` de l’employé :

```bash
GET /api/resource/Salary Slip?filters=[["employee","=","EMP/0001"]]&order_by=end_date desc&limit=1
```

#### 🔹 Option 2 : `Salary Structure Assignment`

Voir s’il a un `Salary Structure` actif :

```bash
GET /api/resource/Salary Structure Assignment?filters=[["employee", "=", "EMP/0001"]]
```

Puis :

```bash
GET /api/resource/Salary Structure/{structure_name}
```

---

### 🛠️ Exemple d'intégration dans ta fiche employé (Spring Boot)

1. **Appelle l’API pour récupérer le dernier `Salary Slip`**
2. Tu extraits : `base`, `gross_pay`, `net_pay`, `earnings[]`, `deductions[]`
3. Tu affiches ces infos dans l’interface RH

---

### ⚙️ API à utiliser dans Frappe

* `Salary Slip`:
  `/api/resource/Salary Slip`
* `Salary Structure`:
  `/api/resource/Salary Structure`
* `Salary Component`:
  `/api/resource/Salary Component`
* `Salary Structure Assignment`:
  `/api/resource/Salary Structure Assignment`

---

## ✅ Prochaine étape

Souhaites-tu que je t’aide à :

1. Écrire un **endpoint Spring Boot** qui affiche les salaires depuis Frappe ?
2. Construire un **module dans Frappe** qui expose une API plus simple, ex. `/get_employee_salary_details` ?
3. Générer un `Salary Slip` via Spring Boot en appelant Frappe ?

Dis-moi ce que tu veux faire précisément et je t’accompagne étape par étape.
