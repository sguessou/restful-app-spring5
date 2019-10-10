package com.sguessou.app.ws.mobileappws.exceptions;

import com.sguessou.app.ws.mobileappws.ui.model.response.ErrorMessages;

public class UserServiceException extends RuntimeException{

    private static final long serialVersionUID = -2607569644147984171L;

    public UserServiceException(String message) {
        super(message);
    }
}
