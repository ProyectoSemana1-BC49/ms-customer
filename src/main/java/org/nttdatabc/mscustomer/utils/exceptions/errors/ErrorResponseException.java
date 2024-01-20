package org.nttdatabc.mscustomer.utils.exceptions.errors;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponseException extends Exception {
    HttpStatus httpStatus;
    int status;
    public ErrorResponseException(String messaage, int status, HttpStatus httpStatus){
        super(messaage);
        this.httpStatus = httpStatus;
        this.status = status;
    }
}
