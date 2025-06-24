package itu.eval3.newapp.client.enums;

public enum AttendanceType {
    Present(),
    Absent(),
    OnLeave("On Leave"),
    HalfDay("Half Day"),
    WorkFromHome("Work From Home");
    
    private final String value;

    private AttendanceType(){
        this.value = this.name();
    }
    private AttendanceType(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
