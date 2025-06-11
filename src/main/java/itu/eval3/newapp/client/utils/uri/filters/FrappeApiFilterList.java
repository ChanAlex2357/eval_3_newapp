package itu.eval3.newapp.client.utils.uri.filters;


import lombok.Data;

@Data
public class FrappeApiFilterList {
    private FrappeApiFilter[] filters;
    public FrappeApiFilterList(FrappeApiFilter[] filters){
        this.filters = filters;
    }
    // public FrappeApiFilterList(String[] fieldnames,String[] operators,String[] values){
    //     List<FrappApiFilter> filters = new ArrayList<>();
    //     String current_value = null;
    //     String curr_op = null;
    //     String curr_fld = null;
    //     for (int i = 0; i < fieldnames.length; i++) {
    //         current_value = values[i];
    //         curr_op = operators[i];
    //         curr_fld = fieldnames[i];
    //         // check null
    //         if (current_value == null || current_value == "") {
    //             continue;
    //         }

    //         if (curr_op.equals("like") && !curr_fld.contains("%")) {
    //             current_value = "%"+current_value+"%";
    //         }

    //         // check like null
    //         if (current_value.equals("%null%") || current_value.equals("%%")) {
    //             continue;
    //         }
    //         filters.add( new FrappApiFilter(curr_fld, curr_op, current_value));
    //     }
        
    //     setFilters( filters.toArray(new FrappApiFilter[0]));   
    // }
}