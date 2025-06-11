package itu.eval3.newapp.client.services.gender;

import java.util.List;

import org.springframework.stereotype.Service;

import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.annexe.Gender;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.limiter.FrappeLimiterComponent;

@Service
public class GenderService extends FrappeCrudService<Gender> {
    public List<Gender> getAll(UserErpNext user) throws ERPNexException {
        List<Gender> genders = null;
        genders = getAllDocuments(
            user,
            new Gender(),
            Gender.class, 
            null, 
            null,
            FrappeLimiterComponent.NOLIMITER,
            null
        );
        return genders;
    }
}
