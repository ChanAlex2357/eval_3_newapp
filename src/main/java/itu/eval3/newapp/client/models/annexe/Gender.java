package itu.eval3.newapp.client.models.annexe;

import itu.eval3.newapp.client.models.action.FrappeDocument;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class Gender extends FrappeDocument{

    public Gender(){ super("Gender"); }
    
    @Override
    public void update_cotnrole() {
    }

    @Override
    public void save_controle() throws Exception{
    }

    @Override
    public Map<String,Object> as_dict() {
        return null;
    }
    
}
