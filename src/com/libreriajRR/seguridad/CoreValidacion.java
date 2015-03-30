
package com.libreriajRR.seguridad;
import com.libreriajRR.util.Empty;

public class CoreValidacion {
    
    static{
    }
    
    public static boolean isLetter(char value){
        return  Character.isLetter(value);
    }
    
    public static boolean isNumber(char value){
        return  Character.isDigit(value);
    }
    
    public static boolean isUpper(char value){
        return  Character.isUpperCase(value);
    }
    
    public static boolean isLower(char value){
        return  Character.isLowerCase(value);
    }
    
    public static boolean isSimbol(char value){
        return  (Character.getType(value) == Character.OTHER_SYMBOL) 
                || (Character.getType(value) == Character.CURRENCY_SYMBOL) 
                || (Character.getType(value) == Character.MATH_SYMBOL);
    }
    
    public static boolean isExistInCadena(char value, String cadena){
        boolean continuar = Empty.EMTPY_BOOLEAN;
        
        for (int ixChar = 0;  ixChar < cadena.length() ; ixChar++) {
            if (value == cadena.charAt(ixChar)){
                continuar = true;
                break;
            }
        }
        
        return  continuar;
    }
    
}
