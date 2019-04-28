package com.sm.beans;
 
public class privilege {
    private int id; 
    private int admit_id; 
    private String p_name; 

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public void setAdmit_id(int admit_id){
        this.admit_id=admit_id;
    }

    public int getAdmit_id(){
        return admit_id;
    }

    public void setP_name(String p_name){
        this.p_name=p_name;
    }

    public String getP_name(){
        return p_name;
    }
}