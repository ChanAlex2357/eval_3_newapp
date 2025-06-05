package itu.eval3.newapp.client.models.autres;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Gender extends FrappeDocument {
    private String gender;

    public Gender() {
        super("Gender");
    }

    @Override
    public void update_cotnrole() {
    }

    @Override
    public void save_controle() {
    }
    
}
