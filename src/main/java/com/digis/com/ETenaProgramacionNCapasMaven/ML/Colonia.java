
package com.digis.com.ETenaProgramacionNCapasMaven.ML;

public class Colonia {
    private int idColonia;
    private String Nombre;
    private String CodigoPostal;
    public Municipio Municipio;

    public Colonia(){
    
    }
    
    public Colonia(int idColonia, String Nombre, String CodigoPostal, Municipio Municipio) {
        this.idColonia = idColonia;
        this.Nombre = Nombre;
        this.CodigoPostal = CodigoPostal;
        this.Municipio = Municipio;
    }

    public int getIdColonia() {
        return idColonia;
    }

    public void setIdColonia(int idColonia) {
        this.idColonia = idColonia;
    }

    public Municipio getMunicipio() {
        return Municipio;
    }

    public void setMunicipio(Municipio Municipio) {
        this.Municipio = Municipio;
    }
   
    
    public int getidColonia(){
        return idColonia;
    }
    
    public void setidColonia(int idColonia){
        this.idColonia = idColonia;
    }
    
    public String getNombre(){
        return Nombre;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    
    public String getCodigoPostal(){
        return CodigoPostal;
    }
    
    public void setCodigoPostal(String CodigoPostal){
        this.CodigoPostal = CodigoPostal;
    }   
}
