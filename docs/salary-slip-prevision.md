# Prevision Salary Slip

Excellente question. Et **non**, tu as raison :

> 🔒 **Le `Salary Slip` ne sert qu’à enregistrer les salaires réels passés ou en cours**, pas pour prévoir les salaires futurs.

---

## 🧭 Pour les salaires futurs : tu dois utiliser le **Salary Structure Assignment**

### 📘 Qu’est-ce que `Salary Structure Assignment` ?

C’est le document qui :

* Lie un **employé** à une **structure salariale** (`Salary Structure`)
* Définit la **date de début** (et éventuellement la date de fin)
* Contient le **montant du salaire de base**, les allocations, les déductions, etc.

---

## 🔍 Exemple d’appel API

### 🔸 Récupérer l’affectation salariale active

```http
GET /api/resource/Salary Structure Assignment?filters=[
  ["employee", "=", "EMP-0001"],
  ["from_date", "<=", "2025-07-01"],
  ["docstatus", "=", 1]
]&order_by=from_date desc&limit=1
```

> Cela te donne l’affectation salariale la plus récente et active à une date donnée.

---

## 🔮 Scénario salaire futur

Si tu veux **prévoir le salaire pour juillet 2025**, voici comment faire :

1. Récupère l’**affectation salariale active à cette date**
2. Récupère le **Salary Structure** associé
3. Applique les règles de calcul (éventuellement côté Java ou avec Frappe)

   * Ou appelle un **simulateur** côté Frappe (voir ci-dessous)

---

## ✅ Alternative : appel à Frappe pour prévisualiser un slip **sans le soumettre**

Tu peux :

* Créer un `Salary Slip` en **`docstatus = 0`** (non soumis)
* Laisser Frappe faire les calculs
* Lire les champs comme `net_pay`, `earnings`, etc.
* Puis le **supprimer** si tu ne veux pas le garder

---

## 🔧 Exemple via API

```json
POST /api/resource/Salary Slip
{
  "employee": "EMP-0001",
  "start_date": "2025-07-01",
  "end_date": "2025-07-31",
  "payroll_frequency": "Monthly",
  "salary_structure": "SAL-STRUCT-001"
}
```

Puis :

```http
POST /api/method/hrms.payroll.doctype.salary_slip.salary_slip.calculate_net_pay
```

Et tu récupères les données sans soumettre le document (donc sans impact).

---

## ✅ Résumé

| Objectif                             | Frappe Doc Type                                              | Utiliser ?      |
| ------------------------------------ | ------------------------------------------------------------ | --------------- |
| Salaire passé réel                   | `Salary Slip (submitted)`                                    | ✅ Oui           |
| Salaire actuel ou futur prévisionnel | `Salary Structure Assignment` + calcul local ou appel Frappe | ✅ Recommandé    |
| Simuler le salaire futur             | `Salary Slip` temporaire non soumis                          | ✅ Astuce fiable |

---

Souhaites-tu que je t’écrive un **service Java Spring Boot** pour :

* Prévoir le salaire d’un mois futur ?
* Simuler un `Salary Slip` ?
* Ou extraire une affectation salariale ?
