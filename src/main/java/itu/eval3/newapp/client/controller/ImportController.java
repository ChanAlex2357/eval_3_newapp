package itu.eval3.newapp.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.action.ImportCsvEval3;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.ImportService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/import/v3")
public class ImportController {
    @Autowired
    private ImportService importService;

    @GetMapping
    public String form(Model model) {
        ImportCsvEval3 importCsvEval3 = new ImportCsvEval3();
        model.addAttribute("importer", importCsvEval3);
        return "import/v3/form";
    }

}
