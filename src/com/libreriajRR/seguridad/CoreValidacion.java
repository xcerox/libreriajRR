
package com.libreriajRR.seguridad;
import com.libreriajRR.util.Empty;

public class CoreValidacion {
    
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
    
    public static boolean isSymbol(int ascii){
        final int rangoTeclas = 32;
        final int minNumero = 48;
        final int maxNumero = 57;
        final int minUpper = 65;
        final int maxUpper = 90;
        final int minLower = 97;
        final int maxLower = 122;
        boolean indicadorSimbolo = Empty.EMPTY_BOOLEAN;

        if(ascii > rangoTeclas){
            if ((ascii < minNumero) & (ascii > maxNumero)){
                indicadorSimbolo = true;
            }else if ((ascii < minUpper) & (ascii > maxUpper)){
                indicadorSimbolo = true;
            }else if ((ascii < minLower) & (ascii > maxLower)){
                indicadorSimbolo = true;
            }
        }
        
        return  indicadorSimbolo;
    }
    
    public static boolean isSymbol(char value){
        int valorAscii = (int)value;
        return isSymbol(valorAscii);
    }
    
    public static boolean isExistInCadena(char value, String cadena){
        boolean continuar = Empty.EMPTY_BOOLEAN;
        
        for (int ixChar = 0;  ixChar < cadena.length() ; ixChar++) {
            if (value == cadena.charAt(ixChar)){
                continuar = true;
                break;
            }
        }
        
        return  continuar;
    }
    
}
