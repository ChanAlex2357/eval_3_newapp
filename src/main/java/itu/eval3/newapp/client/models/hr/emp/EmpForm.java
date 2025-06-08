package itu.eval3.newapp.client.models.hr.emp;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmpForm {

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, message = "Le nom doit contenir au moins 2 caractères")
    private String first_name;

    private String last_name;

    @NotNull(message = "La date d'entrée est obligatoire")
    private Date date_of_joining = Date.valueOf(LocalDate.now());

    @NotNull(message = "La date de naissance est obligatoire")
    @Past(message = "La date de naissance doit être dans le passé")
    private Date date_of_birth;

    @NotBlank(message = "Le genre est obligatoire")
    private String gender;

    @NotBlank(message = "La société est obligatoire")
    private String company;

}
