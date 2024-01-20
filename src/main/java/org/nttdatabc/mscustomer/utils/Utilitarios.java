package org.nttdatabc.mscustomer.utils;

import java.util.UUID;

public class Utilitarios {
    public static String generateUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
