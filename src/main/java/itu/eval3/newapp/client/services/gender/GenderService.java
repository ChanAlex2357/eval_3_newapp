package itu.eval3.newapp.client.services.gender;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Gender;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;

@Service
public class GenderService extends FrappeCrudService<Gender> {
    public List<Gender> getAll(UserErpNext user) throws ERPNexException {
        List<Gender> genders = null;
        genders = getAllDocuments(user,new Gender(), null, null,Gender.class);
        return genders;
    }
}
