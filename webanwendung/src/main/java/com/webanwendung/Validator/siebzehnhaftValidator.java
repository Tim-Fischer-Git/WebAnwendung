package com.webanwendung.Validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

class SiebzehnhaftValidator implements ConstraintValidator<Siebzehnhaft,String> {

    public boolean isValid(String beschreibung, ConstraintValidatorContext context) {
        return beschreibung.contains("siebzehn") || beschreibung.contains("Siebzehn") || beschreibung.contains("17");
    }
}