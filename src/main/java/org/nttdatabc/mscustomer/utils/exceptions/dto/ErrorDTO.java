package org.nttdatabc.mscustomer.utils.exceptions.dto;

import lombok.*;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDTO {
    private HttpStatus httpStatus;
    private String message;
    private int code;
}
