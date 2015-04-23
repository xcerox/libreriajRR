
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


public class Rsa {
    
    {
        instanciaEncriptacion = "RSA/ECB/PKCS1Padding";
        instanciaLlaves = "RSA";
        mensajeError = Empty.EMPTY_STRING;
        textoReEncriptado = Empty.EMPTY_STRING;
    }
    
    private PublicKey llavePublica;
    private PrivateKey llavePrivada;
    private final String instanciaEncriptacion;
    private final String instanciaLlaves;
    private EntidadRsa entidadResultado;
    private String mensajeError;
    private String textoReEncriptado;
    
    private boolean generarLLaves() throws Exception{
        KeyPairGenerator generadorParLlaves = KeyPairGenerator.getInstance(instanciaLlaves);
        KeyPair llaves = generadorParLlaves.generateKeyPair();
        llavePublica = llaves.getPublic();
        llavePrivada = llaves.getPrivate();
        return  ((llavePrivada.toString() != Empty.EMPTY_STRING) && (llavePublica.toString() != Empty.EMPTY_STRING));
    }
    
    private PublicKey loadllavePublica(final String llavePublica) throws Exception{
        final byte[] byteLlave = llavePublica.getBytes();   
        KeyFactory creadorLlaves = KeyFactory.getInstance(instanciaLlaves);
        KeySpec llave = new X509EncodedKeySpec(byteLlave);
        PublicKey _llavePublica = creadorLlaves.generatePublic(llave);
        return _llavePublica;
    }
    
    private PrivateKey loadllavePrivada(final String llavePrivada) throws Exception{
    final byte[] byteLlave = llavePrivada.getBytes();   
    KeyFactory creadorLlaves = KeyFactory.getInstance(instanciaLlaves);
    KeySpec llave = new PKCS8EncodedKeySpec(byteLlave);
    PrivateKey _llavePrivada = creadorLlaves.generatePrivate(llave);
    return _llavePrivada;
    }
    
    private byte[] encriptador(final int modo, final Key llave, final String contenido) throws Exception{
        Cipher EncriptadorRSA = Cipher.getInstance(instanciaEncriptacion);
        EncriptadorRSA.init(modo, llave);
        return  EncriptadorRSA.doFinal(contenido.getBytes());
    }
    
    private byte[] encriptarTexto(final String texto){
        byte[] textoEncriptado = new byte[0];
        try{
            if(generarLLaves()){
              final byte[] textoPreEncriptado = encriptador(Cipher.ENCRYPT_MODE, llavePublica, texto);
              textoEncriptado = Base64.getEncoder().encode(textoPreEncriptado);
            }else{
                mensajeError = "No se a podido encriptar";
            }
        }catch(Exception error){
            mensajeError = error.getMessage();
        }
        return textoEncriptado;
    }
    
    private String escribirLlave(final Key llave){
        return new String(llave.getEncoded());
    }
   
    public boolean encriptar(final String texto){
        entidadResultado = new EntidadRsa();
        final boolean continuar;
        final String contendoCifrado;
        final String _llavePublica;
        final String _llavePrivada;
        
        if(texto.isEmpty()){
            contendoCifrado = Empty.EMPTY_STRING;
            _llavePrivada = Empty.EMPTY_STRING;
            _llavePublica = Empty.EMPTY_STRING;
            continuar = Empty.EMPTY_BOOLEAN;
            mensajeError = "El texto no puede estar en blanco";
            
        }else{
            contendoCifrado = new String(encriptarTexto(texto));
            _llavePrivada = escribirLlave(this.llavePrivada);
            _llavePublica = escribirLlave(this.llavePublica);
            continuar = true;
        }
        
        entidadResultado.setContenidoCifrado(contendoCifrado);
        entidadResultado.setLlavePrivada(_llavePrivada);
        entidadResultado.setLlavePublica(_llavePublica);
        
        return continuar;
    }
    
    public boolean reEncriptarTexto(final String texto, final String llavePublicaTexto){
        boolean continuar = Empty.EMPTY_BOOLEAN;
        if (!texto.isEmpty() && !llavePublicaTexto.isEmpty()){
            try{
                this.llavePublica = loadllavePublica(llavePublicaTexto);
                final byte[] textoPreEncriptado = encriptador(Cipher.ENCRYPT_MODE, this.llavePublica, texto);
                final byte[] textoEncriptado = Base64.getEncoder().encode(textoPreEncriptado);
                textoReEncriptado = new String(textoEncriptado);
                continuar = true;
            }catch(Exception error){
                error.printStackTrace();
                mensajeError = "No se a podido encriptar el texto";
            }finally{
                return continuar;
            }
        }else
            return continuar;
    }  
    
    public String getResultadoReEncriptado(){
        return textoReEncriptado;
    }
    
}

