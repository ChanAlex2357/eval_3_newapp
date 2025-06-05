# frappe_app/eval_app/api/dashboard.py
import frappe
from frappe import _
from collections import defaultdict
from frappe.utils import getdate, formatdate

@frappe.whitelist()
def get_salary_stats(year=None):
    if not year:
        year = frappe.utils.now_datetime().year

    result = []
    monthly_data = defaultdict(lambda: {
        "total_net": 0,
        "total_gross": 0,
        "components": defaultdict(float)
    })

    slips = frappe.get_all("Salary Slip", 
        filters={
            "docstatus": 1,
            "start_date": ["between", [f"{year}-01-01", f"{year}-12-31"]]
        },
        fields=["name", "employee", "employee_name", "start_date", "net_pay", "gross_pay"]
    )

    for slip in slips:
        month_key = getdate(slip.start_date).strftime("%Y-%m")
        monthly_data[month_key]["total_net"] += slip.net_pay
        monthly_data[month_key]["total_gross"] += slip.gross_pay

        earnings = frappe.get_all("Salary Detail", 
            filters={"parent": slip.name, "parenttype": "Salary Slip", "parentfield": "earnings"},
            fields=["salary_component", "amount"])

        deductions = frappe.get_all("Salary Detail", 
            filters={"parent": slip.name, "parenttype": "Salary Slip", "parentfield": "deductions"},
            fields=["salary_component", "amount"])

        for e in earnings:
            monthly_data[month_key]["components"][e.salary_component] += e.amount

        for d in deductions:
            monthly_data[month_key]["components"][d.salary_component] -= d.amount  # négatif pour les déductions

    # formater la sortie finale
    for month in sorted(monthly_data.keys()):
        entry = monthly_data[month]
        result.append({
            "month": month,
            "total_net": entry["total_net"],
            "total_gross": entry["total_gross"],
            "components": dict(entry["components"])
        })

    return {"months": result}
