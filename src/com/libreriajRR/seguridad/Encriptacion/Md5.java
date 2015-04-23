    
package com.libreriajRR.seguridad.Encriptacion;
import java.util.Base64;
import java.security.MessageDigest;
import com.libreriajRR.util.Empty;

//clase de cifrado MD5 simple para cifrar informacion pero no Descifrar

public class Md5 {
    
    {
        textoCifrado = Empty.EMPTY_STRING;
        mensajeError = Empty.EMPTY_STRING;
    }
    
    private String textoCifrado;
    private String mensajeError;
    
    public Md5() {
       
    }
    
    private byte[] cifrar(final String texto) throws Exception{
        
    MessageDigest cifrador = MessageDigest.getInstance("MD5");
    cifrador.update(texto.getBytes());
    
    final byte[] textoPreCifrado = cifrador.digest();
    final byte[] textoCifradoCompleto = Base64.getEncoder().encode(textoPreCifrado);
        
    return textoCifradoCompleto;
    }

    public boolean DoCifrarTexto(final String texto){
        boolean continuar = Empty.EMPTY_BOOLEAN;
        
        try{
            if(texto.isEmpty()){
                this.textoCifrado = Empty.EMPTY_STRING;
                this.mensajeError = "El texto esta en blanco";
            }else{
                this.textoCifrado = new String(cifrar(texto));
                continuar = true;
            }
            
        }catch(Exception error){
            mensajeError = error.getMessage();
        }
        
        return continuar;
    }
        
    public String getResultado(){
        return this.textoCifrado;
    }

    public String getMensajeError() {
        return this.mensajeError;
    }
    
}