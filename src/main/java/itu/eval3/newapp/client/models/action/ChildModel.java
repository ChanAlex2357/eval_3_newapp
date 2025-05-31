package itu.eval3.newapp.client.models.action;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class ChildModel extends FrappeDocument{
        // Document relationship
        private String parent;
        private String parentfield;
        private String parenttype;

        public ChildModel (String doctype){
                super(doctype);
        }
}
