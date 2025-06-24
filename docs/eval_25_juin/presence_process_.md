# Frappe HRMS - Présences et Horaires de Travail

## Présences et Horaires de Travail dans Frappe HRMS

Dans **ERPNext HRMS**, le module **"Shift and Attendance"** correspond à la **gestion des présences et des horaires de travail des employés**. Voici une explication détaillée de ses **composants** et **objectifs** :

---

### 🧩 Objectif principal

Gérer :

* les **plannings horaires** des employés (shifts),
* le **pointage** (manual ou automatique),
* la **présence / absence** quotidienne,
* l'intégration des présences dans le calcul **des salaires** et des **congés**.

---

### 🔧 Composants du module

| Doctype / Fonction                 | Description                                                                                                                           |
| ---------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Shift Type**                     | Définit un type d’horaire : heure de début, fin, pause, jours ouvrés, règles de retard/avance, tolérance.                             |
| **Shift Assignment**               | Associe un ou plusieurs employés à un planning (Shift Type) pour une période donnée.                                                  |
| **Employee Checkin**               | Saisie automatique ou manuelle des heures d’entrée/sortie (badges, biométrie, etc.). Peut être utilisé pour reconstituer la présence. |
| **Attendance**                     | Représente la présence effective d’un employé à une date donnée. Statut : Present, Absent, Half Day, On Leave.                        |
| **Attendance Request** (optionnel) | Soumission par un employé d’une demande de correction de présence ou de justification.                                                |
| **Attendance Settings**            | Paramètres globaux : méthode de marquage de présence, source par défaut (checkin, import, manuel), tolérances.                        |

---

### 🧠 Fonctionnement logique

1. 🔧 **Configurer les Shift Types** (ex: 08h00-17h00, L-V)
2. 🔄 **Assigner les shifts aux employés** (par date/période)
3. ⏱️ **Collecter les check-in/out** (via table `Employee Checkin`)
4. 🧾 **Générer les `Attendance`** (automatiquement ou manuellement)
5. 📆 **Analyser les présences/absences** → utilisé dans le **Salary Slip**

---

### 📌 Intégration avec le reste du HRMS

* **Salary Slip** : récupère les absences (Leave Without Pay, Absent Days) → influence `payment_days`
* **Leave Application** : les congés validés sont automatiquement exclus de l'absentéisme
* **Payroll Entry** : tient compte de la présence réelle
* **Performance/HR Analytics** : permet des dashboards sur la ponctualité, absentéisme, etc.

---

### 🧰 Exemple de cas d’usage

> Une entreprise configure :
>
> * Un **Shift Type** 08h00-17h00
> * Affecte ce shift aux employés
> * Les employés pointent avec une machine biométrique → enregistre `Employee Checkin`
> * Le système génère automatiquement les `Attendance`
> * Lors de la paie, un employé absent 3 jours aura une retenue selon la configuration

---

### 🔄 Pour ton projet Spring Boot

Si tu veux **gérer les présences** à la manière de Frappe HRMS, tu peux t’inspirer des entités suivantes :

* `ShiftType`
* `ShiftAssignment`
* `EmployeeCheckin`
* `Attendance`

Souhaites-tu un **modèle de base de données simplifié** pour l'intégrer dans ton projet ?
