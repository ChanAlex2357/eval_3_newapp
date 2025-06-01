# Salary Slip process

> **Dans Frappe HRMS, le `Salary Slip` est le document *officiel* qui contient le salaire réel d’un employé pour un mois donné.**

---

## ⚙️ Pourquoi faut-il générer un `Salary Slip` ?

### 🔸 1. Le `Salary Structure` seul ne suffit pas

* Il définit **les règles de calcul** (ex : base, primes, déductions).
* Mais **il ne reflète pas les absences, congés, ou heures supplémentaires** du mois.

### 🔸 2. Le `Salary Slip` est calculé

* En générant le `Salary Slip`, Frappe :

  * Applique le `Salary Structure`
  * Calcule les **gains** et **déductions**
  * Tient compte des absences ou congés
  * Produit les montants `gross_pay`, `net_pay`, etc.

---

## 🔄 Workflow complet (automatisé ou via API)

### 1. Vérifier s'il y a déjà un `Salary Slip`

```http
GET /api/resource/Salary Slip?filters=[
  ["employee", "=", "EMP-0001"],
  ["month", "=", "05"],
  ["fiscal_year", "=", "2025"]
]
```

### 2. S’il n’existe pas ➡️ tu dois le **générer** (POST)

**API :**

```http
POST /api/resource/Salary Slip
```

**Données JSON :**

```json
{
  "employee": "EMP-0001",
  "start_date": "2025-05-01",
  "end_date": "2025-05-31",
  "payroll_frequency": "Monthly",
  "salary_structure": "SAL-STRUCT-001"
}
```

➡️ Ensuite, tu dois appeler `/api/method/hrms.payroll.doctype.salary_slip.salary_slip.calculate_net_pay` ou soumettre le document pour le calcul.

---

## 🔐 Astuce : soumettre le `Salary Slip`

Après création, tu dois :

```http
POST /api/resource/Salary Slip/SLIP-ID/submit
```

➡️ Cela "valide" le slip et calcule tous les champs automatiquement.

---

## ✅ Résultat

Une fois le `Salary Slip` généré et soumis, il contient :

* `earnings[]`
* `deductions[]`
* `gross_pay`
* `net_pay`
* `remarks`
* `status: Submitted`

---

## 🧠 Résumé pour ton application

| Action                       | Méthode Frappe                    | Besoin ?        |
| ---------------------------- | --------------------------------- | --------------- |
| Vérifier les slips existants | GET `/Salary Slip`                | Oui             |
| Générer un nouveau slip      | POST `/Salary Slip`               | Si inexistant   |
| Calculer/soumettre le slip   | POST `/Salary Slip/{name}/submit` | Oui (important) |
| Lire les salaires du mois    | GET `/Salary Slip?filters=...`    | Oui             |

---

Souhaites-tu maintenant un exemple **code Spring Boot complet** pour :

* Créer un slip ?
* Ou bien vérifier, créer et soumettre automatiquement un slip si absent ?
