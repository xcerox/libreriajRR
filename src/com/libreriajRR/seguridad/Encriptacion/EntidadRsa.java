package com.libreriajRR.seguridad.Encriptacion;
import com.libreriajRR.util.Empty;

public class EntidadRsa {
    
    {
        llavePrivada = Empty.EMPTY_STRING;
        llavePublica = Empty.EMPTY_STRING;
        ContenidoCifrado = Empty.EMPTY_STRING;
    }

    public EntidadRsa() {
    
    }
    
    public EntidadRsa(String llavePublica,String llavePrivada,String contendioEncriptado) {
        this.ContenidoCifrado = llavePublica;
        this.llavePrivada = llavePrivada;
        this.llavePublica = llavePublica;
    }
    
    private String llavePublica;
    private String llavePrivada;
    private String ContenidoCifrado;    

    public void setLlavePublica(String llavePublica) {
        this.llavePublica = llavePublica;
    }

    public void setLlavePrivada(String llavePrivada) {
        this.llavePrivada = llavePrivada;
    }

    public void setContenidoCifrado(String ContenidoCifrado) {
        this.ContenidoCifrado = ContenidoCifrado;
    }

    public String getLlavePublica() {
        return llavePublica;
    }

    public String getLlavePrivada() {
        return llavePrivada;
    }

    public String getContenidoCifrado() {
        return ContenidoCifrado;
    }  
}
