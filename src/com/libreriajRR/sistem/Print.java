
package com.libreriajRR.sistem;

public class Print {

    public static void println(Object value){
         System.out.println(value);
    }
    
    public static void print(Object value){
         System.out.print(value);
    }
    
    public static void lineaEmpty(){
         System.out.println();
    }
    
    public static void printfn(String formato,Object... value){
        System.out.printf(formato, value);
    }
}
