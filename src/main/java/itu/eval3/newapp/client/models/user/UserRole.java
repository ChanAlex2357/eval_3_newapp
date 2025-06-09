package itu.eval3.newapp.client.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserRole extends FrappeDocument {

    private String role;
    private String parent;
    @JsonProperty("parentfield")
    private String parentField;
    @JsonProperty("parenttype")
    private String parentType;

    public UserRole(){
        super("Has Role");
    }

    @Override
    public void save_controle() throws Exception {}

    @Override
    public void update_cotnrole() {}

    @Override
    public Object as_dict() {
        return null;
    }
}
