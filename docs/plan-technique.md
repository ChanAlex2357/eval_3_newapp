# Plan technique pour l'application Spring Boot RH connectée à Frappe HRMS

Excellent, tu as une vision claire de ton application Spring Boot RH connectée à Frappe HRMS. Voici un **plan technique détaillé** pour répondre **exactement** à ces besoins 👇

---

## ✅ Fonctionnalités demandées + Solutions

---

### 1. 🔍 **Liste des employés avec filtre de recherche**

**💡 Solution :**
Créer un endpoint Spring Boot `/employees` avec des paramètres de recherche (`nom`, `matricule`, `service`, etc.) et consommer l'API Frappe `/api/resource/Employee`.

**📦 Exemple Endpoint :**

```java
@GetMapping("/employees")
public List<EmployeeDTO> getEmployees(@RequestParam(required = false) String keyword) {
    // Appel API Frappe avec filtre sur employee_name ou employee_id
}
```

**🧩 Exemple appel API Frappe :**

```http
GET /api/resource/Employee?filters=[["employee_name","like","%Ali%"]]
```

---

### 2. 👤 **Fiche employé avec ses salaires par mois**

**💡 Solution :**

* Spring Boot appelle Frappe `/api/resource/Salary Slip` avec `employee` comme filtre
* Trie par `start_date DESC`
* Regroupe les résultats **par mois** côté Spring

**📦 Exemple Endpoint Spring :**

```java
@GetMapping("/employees/{id}/salaries")
public List<SalaryByMonthDTO> getEmployeeSalaries(@PathVariable String id) {
    // Appel de Salary Slip, regroupement par mois
}
```

**🧩 Exemple API Frappe :**

```http
GET /api/resource/Salary Slip?filters=[["employee","=","EMP-0001"]]&order_by=start_date desc
```

---

### 3. 📄 **Fiche de paie d’un mois avec export PDF soigné**

**💡 Solution :**

* Récupérer les données de la `Salary Slip` de l’employé pour le mois donné
* Générer un PDF avec **iText**, **Apache PDFBox**, ou **OpenPDF** en Java
* Mise en forme : logo, tableau des gains/déductions, total net, signature

**📦 Exemple :**

```java
@GetMapping("/employees/{id}/salary-slip")
public ResponseEntity<byte[]> getSalarySlipPdf(
    @PathVariable String id,
    @RequestParam int month,
    @RequestParam int year
) {
    // Appel de Salary Slip via API Frappe
    // Génération PDF et retour en bytes
}
```

**🧩 Exemple API Frappe :**

```http
GET /api/resource/Salary Slip?filters=[["employee","=","EMP-0001"], ["month","=","05"], ["fiscal_year","=","2025"]]
```

---

### 4. 📊 **Tableau récap par mois avec tous les employés et leur salaire**

**💡 Solution :**

* Appel groupé de tous les `Salary Slip` du mois
* Construction d’un tableau avec colonnes :

  * Nom employé
  * Base
  * Gains
  * Déductions
  * Net à payer

**📦 Endpoint suggéré :**

```java
@GetMapping("/salaries")
public List<EmployeeSalaryLineDTO> getSalariesForMonth(
    @RequestParam int month,
    @RequestParam int year
) {
    // Appel API Frappe pour tous les slips du mois
    // Construction tableau
}
```

**🧩 Exemple API Frappe :**

```http
GET /api/resource/Salary Slip?filters=[["month","=","05"], ["fiscal_year","=","2025"]]
```

---

## 🛠️ Structuration des DTO côté Spring Boot

### `EmployeeDTO`

```java
public class EmployeeDTO {
    private String id;
    private String name;
    private String department;
    private String designation;
}
```

### `SalarySlipDTO`

```java
public class SalarySlipDTO {
    private String month;
    private String year;
    private BigDecimal grossPay;
    private BigDecimal netPay;
    private List<SalaryComponentDTO> earnings;
    private List<SalaryComponentDTO> deductions;
}
```

### `EmployeeSalaryLineDTO`

```java
public class EmployeeSalaryLineDTO {
    private String employeeName;
    private BigDecimal base;
    private BigDecimal earningsTotal;
    private BigDecimal deductionsTotal;
    private BigDecimal net;
}
```

---

## 🔐 Authentification à l'API Frappe

* Utilise les **token d’API** ou authentification par `Session`.
* Pour l’instant, tu peux inclure le token dans le `Authorization` header.

---

## ✍️ Prochaine étape

Souhaites-tu que je t’aide maintenant à :

1. Implémenter une **fonction Spring Boot complète** (ex. génération PDF d’un salaire) ?
2. Écrire une **API Frappe personnalisée** si les données ne sont pas facilement accessibles ?
3. Concevoir un **modèle de base pour tes DTO + services de synchronisation** ?

Tu peux aussi me dire avec quel framework PDF tu travailles (ou veux travailler), et je t’écris le code complet du PDF.
