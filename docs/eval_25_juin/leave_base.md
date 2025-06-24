# Leave Management Feature

## Introduction

Voici une proposition complète :

---

### ✅ 1. `LeaveController.java`

```java
package itu.eval3.newapp.client.controllers;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.leave.LeaveApplication;
import itu.eval3.newapp.client.models.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hr/leaves")
@RequiredArgsConstructor
public class LeaveController {

    private final LeaveService leaveService;

    @GetMapping
    public String showLeavePage(Model model, @RequestParam(required = false) String employee) throws ERPNexException {
        UserErpNext user = UserErpNext.mock(); // TODO: Replace with authenticated user

        if (employee != null) {
            List<LeaveBalanceDTO> balances = leaveService.getLeaveBalance(user, employee);
            model.addAttribute("balances", balances);
            model.addAttribute("employeeId", employee);
        }

        model.addAttribute("leaveForm", new LeaveApplication());
        return "hr/leaves/index";
    }

    @PostMapping
    public String submitLeave(@ModelAttribute LeaveApplication leaveForm, Model model) throws Exception {
        UserErpNext user = UserErpNext.mock(); // TODO: Replace with authenticated user

        leaveService.createLeave(user, leaveForm);

        return "redirect:/hr/leaves?employee=" + leaveForm.getEmployee();
    }
}
```

---

### ✅ 2. `src/main/resources/templates/hr/leaves/index.html`

```html
<div th:replace="~{layouts/default :: layout(title='Assignement Salary',content=~{::content})}">
    <th:block th:fragment="content">
        <div class="row">
            <div class="container">

                <h3>Demande de congé</h3>
                <form th:action="@{/hr/leaves}" th:object="${leaveForm}" method="post">
                    <div class="row mb-2">
                        <div class="col-md-6">
                            <label for="employee">Employé</label>
                            <input type="text" class="form-control" th:field="*{employee}" required>
                        </div>
                        <div class="col-md-6">
                            <label for="leaveType">Type de congé</label>
                            <input type="text" class="form-control" th:field="*{leaveType}" required>
                        </div>
                    </div>

                    <div class="row mb-2">
                        <div class="col-md-6">
                            <label for="fromDate">Date début</label>
                            <input type="date" class="form-control" th:field="*{fromDate}" required>
                        </div>
                        <div class="col-md-6">
                            <label for="toDate">Date fin</label>
                            <input type="date" class="form-control" th:field="*{toDate}" required>
                        </div>
                    </div>

                    <div class="mb-2">
                        <label for="reason">Motif</label>
                        <input type="text" class="form-control" th:field="*{reason}">
                    </div>

                    <div class="mb-2 form-check">
                        <input type="checkbox" class="form-check-input" th:field="*{halfDay}">
                        <label class="form-check-label" for="halfDay">Demi-journée ?</label>
                    </div>

                    <button class="btn btn-primary">Soumettre</button>
                </form>

                <hr>
                <div th:if="${balances}">
                    <h4>Congés restants</h4>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th>Type</th>
                                <th>Alloué</th>
                                <th>Pris</th>
                                <th>Restant</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr th:each="bal : ${balances}">
                                <td th:text="${bal.leaveType}"></td>
                                <td th:text="${bal.allocated}"></td>
                                <td th:text="${bal.taken}"></td>
                                <td th:text="${bal.remaining}"></td>
                            </tr>
                        </tbody>
                    </table>
                </div>

            </div>
        </div>
    </th:block>
</div>
```

---

### ✅ 3. Exemple d'URL test

```txt
GET /hr/leaves?employee=EMP-0001
```

(pour afficher les congés restants de l’employé)

---

Souhaitez-vous aussi :

* Ajouter une sélection d’employés par dropdown ?
* Valider les dates côté back ?
* Afficher l’historique des congés déposés ?

Je peux vous générer tout cela rapidement.
