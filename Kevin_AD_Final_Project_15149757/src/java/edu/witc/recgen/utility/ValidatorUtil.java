/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.witc.recgen.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author 15149757
 */
public class ValidatorUtil {
    public static boolean hasText(String text){
        boolean isValid = false;

        if(text.trim().length() > 0 )
                isValid = true;

        return isValid;
    }//hasText

    public static boolean isPositiveWholeNumber(String text){
        boolean isValid = false;

        if(text.matches("[0-9]+"))
                isValid = true;

        return isValid;
    }//end isPositiveWholeNumber

    public static boolean isAlphabetic(String text) {
            //English language string only, with or without spaces
            // ^[a-zA-Z]+( [a-zA-z]+)*$
            boolean isValid = false;
            // Text only, no numbers, spaces or special characters
            //  [a-zA-Z]+
            //Allow a space but not a dash
            // "^\\p{L}+(?: \\p{L}+)*$"
            //Allows a space and/or dash
            // "^\\p{L}+(?:[ \\p{Pd}]\\p{L}+)*$"
            //String must start with at least one, not space
            //String can contain few words, but every word beside 
            //first must have space before it. Strings from any language
            //if(text.matches("^\\p{L}+(?: \\p{L}+)*$"))  
            if(text.matches("^\\p{L}+(?:[ \\p{Pd}]\\p{L}+)*$"))  
                    isValid = true;

            return isValid;

    } // End of isAlphabetic()

    public static boolean isAnyWholeNumber(String text){
        boolean isValid = false;

        if(text.matches("[-?, 0-9]+"))
                isValid = true;

        return isValid;
    }//end isAnyWholeNumber

    public static boolean isAnyDecimalNumber(String text){
        /*
            ^           # Beginning of string
            [+-]?       # Optional plus or minus character
            (           # Followed by either:
            (           # Start of first option
            \d+         # One or more digits
            (\.\d*)?    # Optionally followed by: one decimal point and zero or more digits
            )           # End of first option
            |           # or
            (\.\d+)     # One decimal point followed by one or more digits
            )           # End of grouping of the OR options
            $           # End of string (i.e. no extra characters remaining)
        */
        boolean isValid = false;
        if(text.matches("^[+-]?((\\d+(\\.\\d*)?)|(\\.\\d+))$")){
            isValid = true;           
        }    
        return isValid;
    }//end isAnyDecimalNumber
    
    public static boolean isAnyPositiveNumber(String text){
        boolean isValid = false;
        text = replaceCharacters(text);
        if(text.matches("(\\+)?([0-9]+((\\.)?[0-9]+))")){
            isValid = true;           
        }    
        return isValid;
    }
     //this will get rid of all non-numerical characters INCLUDING a dot
    public static String replaceNonNumerics(String text){
        text = text.replaceAll("[^\\d]", "");
        return text;
    }
     
    public static String replaceCharacters(String text){
        //first argument is a regular express
        //replace all $ and , and - and ( and ) and spaces with an empty string
        text = text.replaceAll("[\\$,\\,\\-,\\(,\\),\\s]+", "");
   
        return text;
    }
    public static boolean isValidEmail(String emailAddress) {
        Pattern pattern;
        Matcher matcher;
        //This expression handles much more then just making sure the @ 
        //and ._ _ _ is correct...
        final String REGX_EMAIL= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                     + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        //Pattern holds the regular expression
        pattern = Pattern.compile(REGX_EMAIL);
        //matcher object to regex a string against my pattern
        matcher = pattern.matcher(emailAddress);
        //return if email is valid (true) or not (false)
        return matcher.matches();
    }//end isValidEmail

    /**
     * Determines if the credit card number is valid based on the Luhn
     * Algorithm. Credit card must be between 13 and 16 characters long
     * This function can be tested using the number 4111111111111111
     * @param ccNum a string representing a credit card number
     * @return 
     */
    public static boolean isValidCreditCard(String ccNum){

        int index;
        long number = 0;
        boolean alternate = false;
        long total = 0;
        boolean isValid;

        ccNum = ccNum.replace("-", "");
        ccNum = ccNum.replace(" ", "");
        index = ccNum.length();
        
        if(!isPositiveWholeNumber(ccNum)){
            isValid = false;
        }
        else{
            if(ccNum.length() > 12 && ccNum.length() < 17){
                while(index-- > 0){
                    number = Long.parseLong(ccNum.substring(index, index + 1));
                    if(alternate){
                        number *= 2; 

                        if(number > 9)
                            number -= 9; 

                    }
                    total += number;  

                    alternate = !alternate;
                }//end of while loop

                isValid = (total % 10) == 0;

            }//end if for length check
            else{
                isValid = false;
            }//end else for length check
        }//end else for !isPositiveWholeNumber
        return isValid;

    }//end isValidCreditCard
    
}
