package com.iqes.entity.dto;

 public class SimpleRestaurantInfo{
    private String name;
    private String address;
     private String openTime;
     private String endTime;



    public SimpleRestaurantInfo(){

    }

    public SimpleRestaurantInfo(String name,String address,String opentime,String endTime){
        this.name=name;
        this.address=address;
        this.openTime=opentime;
        this.endTime=endTime;
    }

     public String getOpenTime() {
         return openTime;
     }

     public void setOpenTime(String openTime) {
         this.openTime = openTime;
     }

     public String getEndTime() {
         return endTime;
     }

     public void setEndTime(String endTime) {
         this.endTime = endTime;
     }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}