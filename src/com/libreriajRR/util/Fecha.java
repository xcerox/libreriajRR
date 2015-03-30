
package com.libreriajRR.util;
import java.util.Calendar;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
      
public class Fecha {
    {
     calendario = Calendar.getInstance();
    }
    
    public static enum unidad{
        DIA,
        SEMANA,
        MES,
        ANO,
        HORA,
        MINUTO,
        SEGUNDO
    }
    
    private Calendar calendario;

    
    public Date operacion(Date fecha,unidad unidad, int valor){
        
        calendario.setTime(fecha);    
       
        switch(unidad){
            case DIA:{
                calendario.add(Calendar.DATE,valor);
                break;
            }case SEMANA:{
                calendario.add(Calendar.WEEK_OF_YEAR, valor);
                break;
            }case MES:{
                calendario.add(Calendar.MONTH, valor);
                break;
            }case ANO:{
                calendario.add(Calendar.YEAR, valor);
                break;
            }case HORA:{
                calendario.add(Calendar.HOUR, valor);
                break;
            }case MINUTO:{
                calendario.add(Calendar.MINUTE, valor);
                break;
            }case SEGUNDO:{
                calendario.add(Calendar.SECOND, valor);
                break;
            }
        }
        
        return calendario.getTime();
    }
    
     public Date operacion(String fecha,String formato,unidad unidad, int valor) throws ParseException{
         SimpleDateFormat fechaFormato = new SimpleDateFormat(formato);
          return operacion( fechaFormato.parse(fecha), unidad, valor);
     }
}
