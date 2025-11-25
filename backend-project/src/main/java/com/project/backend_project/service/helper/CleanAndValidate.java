package com.project.backend_project.service.helper;

public class CleanAndValidate {

    private CleanAndValidate(){}

     public static String cleanAndValidateNumber(String value, String fieldName){
        if(value == null || value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + "cannot be empty");
        }

        value = value.trim().replaceAll("\\s+", "");

        if(!value.matches("\\d+")){
            throw new IllegalArgumentException(fieldName + "must contain digits only");
        }
        return value;
    }

     public static String cleanAndValidateName(String field, String fieldName){
        if(field == null || field.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
            }
             field = field.trim().replaceAll("\\s+", " ");

            if(!field.matches("^[a-zA-Z ]+$")){
                throw new IllegalArgumentException(fieldName +" must contain only letters");
        }
        return field;
    }


    
}
