package itu.eval3.newapp.client.models.hr.salary.form;

public class SalaryComponentForm {
    public String salary_component;
    public String condition;
    public double amount;
    public boolean amount_based_on_formula;
    public String formula;
    public String type;
    public String getSalary_component() {
        return salary_component;
    }

    public void checkFormula(){
        if (formula != null) {
            setAmount_based_on_formula(true);
        }
    }

    public void setSalary_component(String salary_component) {
        this.salary_component = salary_component;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isAmount_based_on_formula() {
        return amount_based_on_formula;
    }

    public void setAmount_based_on_formula(boolean amount_based_on_formula) {
        this.amount_based_on_formula = amount_based_on_formula;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
