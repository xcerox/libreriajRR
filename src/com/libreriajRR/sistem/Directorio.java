package com.libreriajRR.sistem;

import com.libreriajRR.util.Empty;
import java.io.File;
import java.util.ArrayList;

public class Directorio {

    {
        ext = Empty.EMPTY_STRING;
        buscarEnSubCarpetas = Empty.EMTPY_BOOLEAN;
    }

    private String ext;
    private boolean buscarEnSubCarpetas;
    
    private File[] getArchivosEnDirectorio(File Directorio){
        File[] archivos = Directorio.listFiles();
        ArrayList<File> listaArchivos = new ArrayList<>();
        

        for (File archivo:archivos) {
            if(archivo.isDirectory() && buscarEnSubCarpetas){
                for (File archivoEnDirectorio : getArchivosEnDirectorio(archivo)) {
                   if (archivoEnDirectorio.getName().endsWith(ext)) 
                        listaArchivos.add(archivoEnDirectorio);
                }
            }else{
                if (archivo.getName().endsWith(ext)) 
                    listaArchivos.add(archivo);
            }
        }
        
        return listaArchivos.toArray( new File[0]);
        
    }

    public File[] getArchivos(String path) {

        File Directorio = new File(path);

        if (Directorio.exists()) 
            return getArchivosEnDirectorio(Directorio);
        else
            return new File[0];
            
        
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public void setBuscarEnSubCarpetas(boolean buscarEnSubCarpetas) {
        this.buscarEnSubCarpetas = buscarEnSubCarpetas;
    }
    
    void dispose(){
        
    }
}
