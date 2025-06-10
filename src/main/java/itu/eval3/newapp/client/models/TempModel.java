package itu.eval3.newapp.client.models;


import itu.eval3.newapp.client.models.action.FrappeDocument;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class TempModel extends FrappeDocument{
    public TempModel(){
        super("");
    }

    @Override
    public void save_controle() throws Exception {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update_cotnrole() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Object as_dict() {
        // TODO Auto-generated method stub
        return null;
    }
}
