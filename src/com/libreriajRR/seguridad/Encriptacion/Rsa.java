
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
    
    private PublicKey llavePublica;
    private PrivateKey llavePrivada;
    final String instanciaEncriptacion = "RSA/ECB/PKCS1Padding";
    final String instanciaLlaves = "RSA";
    private EntidadRsa entidadResultado;
    private String mensajeError;
    
    
    private boolean generarLLaves() throws Exception{
        KeyPairGenerator generadorParLlaves = KeyPairGenerator.getInstance(instanciaLlaves);
        KeyPair llaves = generadorParLlaves.generateKeyPair();
        llavePublica = llaves.getPublic();
        llavePrivada = llaves.getPrivate();
        boolean continuar = ((llavePrivada.toString() != Empty.EMPTY_STRING) && (llavePublica.toString() != Empty.EMPTY_STRING));
        return  continuar;
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
    
    public boolean reEncriptarTexto(final String texto, final String _llavePublica){
        
        llavePrivada = loadllavePublica(_llavePublica);
        
    }
}
