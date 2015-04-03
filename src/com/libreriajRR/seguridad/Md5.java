
package com.libreriajRR.seguridad;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import java.security.MessageDigest;
import com.libreriajRR.util.Empty;

//clase de cifrado MD5 simple para cifrar informacion pero no Decifrarlo

public class Md5 {
    
    {
        textoCifrado = Empty.EMPTY_STRING;
    }
    
    private String textoCifrado;
    
    public Md5() {
       
    }
    
    private byte[] cifrar(final String texto) throws Exception{
        
    final MessageDigest cifrador = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
    cifrador.update(texto.getBytes());
    
    final byte[] textoPreCifrado = cifrador.digest();
    final byte[] textoCifradoCompleto = Base64.encodeBase64(textoPreCifrado);
        
    return textoCifradoCompleto;
    }

    public boolean DoCifrarTexto(final String texto) throws Exception{
        boolean continuar = Empty.EMTPY_BOOLEAN;
        
        if(texto.isEmpty())
            this.textoCifrado = Empty.EMPTY_STRING;
        else{
            this.textoCifrado = new String(cifrar(texto));
            continuar = true;
        }
   
        return continuar;
    }
    
    public String GetResultado(){
        return this.textoCifrado;
    }
    
}




