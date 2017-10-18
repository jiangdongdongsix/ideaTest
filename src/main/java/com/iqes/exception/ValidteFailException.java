package com.iqes.exception;

public class ValidteFailException  extends Exception{


    public ValidteFailException(){

    }

    public  ValidteFailException(String error){
        super(error);
    }

}
