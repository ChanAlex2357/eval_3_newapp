Parfait. Voici comment **ajouter une fonction dans ton script Python Frappe** pour **générer la simulation de salaire** et la rendre **appelable depuis un formulaire Spring Boot** via une API :

---

## ✅ Fonction Python Frappe : `simulate_salary_slip`

Ajoute ceci dans un fichier Python de ton app, par exemple `eval_app/payroll/api.py`

```python
import frappe
from frappe import _
from frappe.utils import getdate
from hrms.payroll.doctype.salary_structure.salary_structure import make_salary_slip

@frappe.whitelist(allow_guest=False)
def simulate_salary(employee, salary_structure, date_str, base=None):
    """
    Simule un salaire sans le sauvegarder (sans insert/submit)
    Args:
        employee (str): Nom de l'employé
        salary_structure (str): Structure de salaire
        date_str (str): Date au format yyyy-mm-dd
        base (float): Salaire de base personnalisé
    Returns:
        dict: Résultat simulé (gains, déductions, net, etc.)
    """
    try:
        posting_date = getdate(date_str)

        slip = make_salary_slip(
            source_name=salary_structure,
            employee=employee,
            posting_date=posting_date
        )

        # Surcharge du salaire de base (facultatif)
        if base:
            base = float(base)
            for earning in slip.earnings:
                if earning.salary_component.lower() in ["salaire base", "salaire de base", "salaire fixe"]:
                    earning.amount = base
            slip.calculate_component_amounts()

        slip.run_method("calculate_net_pay")

        return {
            "employee": slip.employee,
            "employee_name": slip.employee_name,
            "structure": slip.salary_structure,
            "start_date": slip.start_date,
            "end_date": slip.end_date,
            "gross_pay": slip.gross_pay,
            "net_pay": slip.net_pay,
            "earnings": [e.as_dict() for e in slip.earnings],
            "deductions": [d.as_dict() for d in slip.deductions],
            "currency": slip.currency
        }

    except Exception as e:
        frappe.log_error(f"Erreur simulation salaire : {frappe.get_traceback()}")
        return {
            "success": False,
            "error": str(e)
        }
```

---

## 🟢 Exemple d’appel HTTP (Spring Boot ou Postman)

```
POST /api/method/eval_app.payroll.api.simulate_salary

Form Data:
- employee: HR-EMP-00001
- salary_structure: Salaire Gasy 2024
- date_str: 2024-07-01
- base: 1500000
```

---

## 🎨 Idée de formulaire côté Spring Boot / Thymeleaf

```html
<form method="post" action="/simulate-salary">
  <div class="row">
    <div class="col-md-4">
      <label>Employé</label>
      <select name="employee" class="form-select" required>
        <option th:each="emp : ${employees}" th:value="${emp.name}" th:text="${emp.fullname}">...</option>
      </select>
    </div>
    <div class="col-md-4">
      <label>Structure</label>
      <select name="structure" class="form-select" required>
        <option th:each="s : ${structures}" th:value="${s.name}" th:text="${s.name}">...</option>
      </select>
    </div>
    <div class="col-md-2">
      <label>Date</label>
      <input type="date" name="date" class="form-control" required>
    </div>
    <div class="col-md-2">
      <label>Salaire Base</label>
      <input type="number" name="base" class="form-control">
    </div>
  </div>
  <div class="mt-3">
    <button class="btn btn-success">Simuler</button>
  </div>
</form>
```

---

Souhaites-tu que je t’aide à :

* Générer le **contrôleur Spring Boot** pour appeler cette API ?
* Ou afficher les **résultats dynamiquement (JSON)** dans la page ?
