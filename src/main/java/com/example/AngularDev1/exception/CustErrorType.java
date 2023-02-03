package com.example.AngularDev1.exception;

import com.example.AngularDev1.dto.UserDTO;

public class CustErrorType extends UserDTO {

    private String ErrorMsg;

    public CustErrorType(final String errorMessage){
        this.ErrorMsg = errorMessage;
    }

    /*@Override*/
    public String getErrorMessage() {
        return ErrorMsg;
    }

}
