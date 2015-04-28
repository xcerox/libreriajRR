
package com.libreriajRR.seguridad.Encriptacion;
import java.security.PublicKey;
import java.security.PrivateKey;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

import java.util.Base64;
import com.libreriajRR.util.Empty;
import java.util.Arrays;
import java.util.ArrayList;


public class Rsa {
    
    {
        instanciaEncriptacion = "RSA/ECB/PKCS1Padding";
        mensajeError = Empty.EMPTY_STRING;
        textoResultado = Empty.EMPTY_STRING;
        llaves = new Llaves();
    }
    
    public class EntidadRsa {

        {
            llavePrivada = Empty.EMPTY_STRING;
            llavePublica = Empty.EMPTY_STRING;
            contenido = Empty.EMPTY_STRING;
        }

        public EntidadRsa() {

        }

        public EntidadRsa(String llavePublica,String llavePrivada,String contendio) {
            this.contenido = contendio;
            this.llavePrivada = llavePrivada;
            this.llavePublica = llavePublica;
        }

        private String llavePublica;
        private String llavePrivada;
        private String contenido;    

        public void setLlavePublica(String llavePublica) {
            this.llavePublica = llavePublica;
        }

        public void setLlavePrivada(String llavePrivada) {
            this.llavePrivada = llavePrivada;
        }

        public void setContenido(String contenido) {
            this.contenido = contenido;
        }

        public String getLlavePublica() {
            return llavePublica;
        }

        public String getLlavePrivada() {
            return llavePrivada;
        }

        public String getContenido() {
            return contenido;
        }
    }
    
    public class Llaves{
        
        {
            this.instanciaLlaves = "RSA";
            this.mensajeError = Empty.EMPTY_STRING;
        }

        public Llaves() {
        }
        
        private PrivateKey llavePrivada;
        private PublicKey llavePublica;
        private final String instanciaLlaves;
        private String mensajeError;

        public boolean generarLLaves() {
            boolean continuar = Empty.EMPTY_BOOLEAN;
            mensajeError = Empty.EMPTY_STRING;
            
            try{
                KeyPairGenerator generadorParLlaves = KeyPairGenerator.getInstance(instanciaLlaves);
                KeyPair llaves = generadorParLlaves.generateKeyPair();
                this.llavePublica = llaves.getPublic();
                this.llavePrivada = llaves.getPrivate();
                continuar = true;
            }catch(Exception error){
                this.mensajeError = error.getMessage();
                error.printStackTrace();
            }
            
            return continuar;
        }
        
        public KeyPair getResultado(){
            return  new KeyPair(llavePublica, llavePrivada);
        }
        
        public String getMensajeError() {
            return mensajeError;
        }
        
        public PublicKey loadllavePublica(final String llavePublica){
            PublicKey llavePublicaCargada = null;
            
            try {
                final byte[] byteEncriptado = llavePublica.getBytes();
                final byte[] byteLlave = Base64.getDecoder().decode(byteEncriptado);
                
                KeyFactory  creadorLlaves = KeyFactory.getInstance(this.instanciaLlaves);
                KeySpec llave = new X509EncodedKeySpec(byteEncriptado);
                llavePublicaCargada = creadorLlaves.generatePublic(llave);
            }catch(Exception error){
                this.mensajeError = error.getMessage();
                error.printStackTrace();
            }
            
            return llavePublicaCargada;
        }

        public PrivateKey loadllavePrivada(final String llavePrivada){
            PrivateKey llavePrivateCargada = null;
            this.mensajeError = Empty.EMPTY_STRING;
            
            try {
                final byte[] byteEncriptado = llavePrivada.getBytes();
                final byte[] byteLlave = Base64.getDecoder().decode(byteEncriptado);
                
                KeyFactory  creadorLlaves = KeyFactory.getInstance(this.instanciaLlaves);
                KeySpec llave = new PKCS8EncodedKeySpec(byteLlave);
                llavePrivateCargada = creadorLlaves.generatePrivate(llave);
            }catch(Exception error){
                this.mensajeError = error.getMessage();
                error.printStackTrace();
            }
            
            return llavePrivateCargada;
        }
        
        public String KeyToString(Key key) throws Exception{
            return Base64.getEncoder().encodeToString(key.getEncoded()); 
        }
    }
    
    
    private final String instanciaEncriptacion;
    private EntidadRsa entidadResultado;
    private Llaves llaves;
    private String mensajeError;
    private String textoResultado;
        
    //region cifrado
    private byte[] cifrador(final int modo, final Key llave, final String contenido) throws Exception{
        Cipher EncriptadorRSA = Cipher.getInstance(instanciaEncriptacion);
        EncriptadorRSA.init(modo, llave);
        return  EncriptadorRSA.doFinal(contenido.getBytes());
    }
    
    private byte[] cifrar(final String texto, PublicKey llavePublica){
        byte[] textoEncriptado = null;
        try{
              final byte[] textoPreEncriptado = cifrador(Cipher.ENCRYPT_MODE, llavePublica, texto);
              textoEncriptado = Base64.getEncoder().encode(textoPreEncriptado);
        }catch(Exception error){
            mensajeError += error.getMessage();
            error.printStackTrace();
        }
        return textoEncriptado;
    }
    
    private byte[] desCifrar(final String textoCifrado, PrivateKey llavePrivada){
        byte[] textoDescifrado = null;
        
        try {
            final byte[] textoPreDescrifrado = Base64.getDecoder().decode(textoCifrado);
            textoDescifrado = cifrador(Cipher.DECRYPT_MODE, llavePrivada, new String(textoPreDescrifrado));
        } catch (Exception error){
            this.mensajeError += error.getMessage();
            error.printStackTrace();
        }
        
        return textoDescifrado;
    }
    
    //end region cifrado
    
    //region encriptado
    public boolean encriptar(final String texto){
        entidadResultado = null;
        this.mensajeError = Empty.EMPTY_STRING;
        boolean continuar = Empty.EMPTY_BOOLEAN;
        
        try{
            if(!texto.isEmpty()){
                if(llaves.generarLLaves()){
                    String llavePrivada = llaves.KeyToString(llaves.getResultado().getPrivate());
                    String llavePublica = llaves.KeyToString(llaves.getResultado().getPublic());
                    entidadResultado = new EntidadRsa(llavePublica,llavePrivada,new String(cifrar(texto,llaves.getResultado().getPublic())));
                    continuar = true;
                }
                else
                    mensajeError += " Error generando las llaves";
            }else
                mensajeError += " El texto no puede estar en blanco";
        }catch(Exception error){
           mensajeError += error.getMessage();
           error.printStackTrace();
        }finally{
            return continuar;
        }
    }
    
    public boolean encriptarConLlave(final String texto, final String LlavePublica){
        boolean continuar = Empty.EMPTY_BOOLEAN;
        this.mensajeError = Empty.EMPTY_STRING;
        if (!texto.isEmpty() && !LlavePublica.isEmpty()){
            try{
                PublicKey llavePublicaCargada = llaves.loadllavePublica(LlavePublica);
                this.textoResultado = new String(cifrar(texto,llavePublicaCargada));
                continuar = true;
            }catch(Exception error){
                error.printStackTrace();
                mensajeError += error.getMessage();
            }
        }else
            mensajeError += " El texto o la llave publica estan en blanco";
        
        return continuar;
    }  
    
    public boolean desencriptar(final String textoEncriptado, final String llavePrivada){
        boolean continuar = Empty.EMPTY_BOOLEAN;
        this.mensajeError = Empty.EMPTY_STRING;
        
        if(!textoEncriptado.isEmpty() && !llavePrivada.isEmpty()){
            try {
             PrivateKey llavePrivadaCargada = llaves.loadllavePrivada(llavePrivada);
             this.textoResultado = new String(desCifrar(textoEncriptado,llavePrivadaCargada));
             continuar = true;   
            } catch (Exception error) {
                this.mensajeError +=  error.getMessage();
                error.printStackTrace();
            }
        }else
            this.mensajeError += " El contenidoEncriptado o la llave privada esta en blanco";
        
        return continuar;
    }
    //end region encriptado
    
    //region salida
    public String getResultado(){
        return textoResultado;
    }
    
    //**este resultado es solo para cuando se encripta
    public EntidadRsa getResultadoRSA(){
        return entidadResultado;
    }

    public String getMensajeError() {
        return mensajeError;
    }
    //end region salida
}

