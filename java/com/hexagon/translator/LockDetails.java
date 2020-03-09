package com.hexagon.translator;

public class LockDetails {
    public int id;
    public String data;
    public String time,title;
    public static final String CREATE_TABLE="CREATE TABLE lockDetails (id INTEGER PRIMARY KEY AUTOINCREMENT,title TEXT,data TEXT,time DATETIME DEFAULT CURRENT_TIMESTAMP)";

     public LockDetails(){

     }
     public LockDetails(int id,String title,String data,String time){
         this.id=id;
         this.data=data;
         this.time=time;
         this.title=title;
     }
     public String getData(){
         return data;
     }
     public void setData(String data){
         this.data=data;
     }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public int getId(){
         return id;
    }
    public String getTime(){
         return time;
    }

}
