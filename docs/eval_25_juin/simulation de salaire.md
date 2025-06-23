Très bonne orientation !

La **prévisualisation ou simulation du salaire** est une **fonctionnalité métier puissante**, car elle permet :

* au **gestionnaire RH** de **vérifier les montants avant soumission**,
* d’**ajuster les composants** (bonus, absence, etc.) sans affecter les vraies données,
* d’**anticiper la masse salariale** pour un mois donné.

---

## 🎯 Objectif : Simuler un Salary Slip sans sauvegarde définitive

Tu veux donc :

1. **Générer un Salary Slip en mémoire** (`draft`, `non enregistré`)
2. Sans créer de **Salary Structure Assignment** ni de `insert()`/`submit()`
3. Afficher le détail : composants, brut, net, impôts, etc.

---

## ✅ Étapes pour implémenter la simulation

### 1. ❌ Ne pas créer d’`assign_salary_structure_for_employees()`

→ Cette fonction crée une ligne réelle dans ERPNext. Inutile ici.

### 2. ✅ Utiliser `make_salary_slip()` manuellement

Tu peux déjà appeler `make_salary_slip(...)` comme tu fais, **mais sans l’enregistrer** :

```python
from hrms.payroll.doctype.salary_structure.salary_structure import make_salary_slip

def simulate_salary(employee_name, structure_name, date, base=None):
    try:
        salary_slip = make_salary_slip(
            source_name=structure_name,
            employee=employee_name,
            posting_date=date
        )

        # Injecter manuellement le salaire de base si précisé
        if base:
            for row in salary_slip.earnings:
                if row.salary_component == "Salaire Base" and not row.amount:
                    row.amount = base
            salary_slip.calculate_component_amounts()

        # Calculer les totaux sans submit()
        salary_slip.run_method("calculate_net_pay")

        # Retourner en JSON (simulé)
        return {
            "name": salary_slip.name,
            "employee": salary_slip.employee,
            "structure": salary_slip.salary_structure,
            "start_date": salary_slip.start_date,
            "end_date": salary_slip.end_date,
            "earnings": [e.as_dict() for e in salary_slip.earnings],
            "deductions": [d.as_dict() for d in salary_slip.deductions],
            "gross_pay": salary_slip.gross_pay,
            "net_pay": salary_slip.net_pay,
            "currency": salary_slip.currency
        }

    except Exception as e:
        frappe.log_error(f"Erreur simulation salaire: {str(e)}")
        raise e
```

---

## 🔁 Bonus : Simulation en **batch** (multi employés)

Tu peux même faire :

```python
def simulate_salaries(employees, structure, date, base_dict):
    return [simulate_salary(emp, structure, date, base_dict.get(emp)) for emp in employees]
```

---

## 👁‍🗨 UI côté Spring/Thymeleaf

* Formulaire :

  * Employee
  * Date du mois
  * Base salariale
  * Choix du Salary Structure

* Résultat :

  * Détail du bulletin (tableau earnings/deductions)
  * Brut, Net, Impôt

---

## 📌 Avantages métier

* Zéro effet de bord
* Contrôle RH avant validation
* Optimisation budgétaire
* Peut servir à la génération automatique de masse salariale

---

Souhaites-tu que je t’aide à :

* coder l’API côté Frappe (méthode `frappe.whitelist`)
* ou construire le `formulaire simulateur` côté Spring/Thymeleaf ?
