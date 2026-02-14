
package com.digis.com.ETenaProgramacionNCapasMaven.ML;

public class Direccion {
    private int idDireccion;
    private String Calle;
    private String NumeroInterior;
    private String NumeroExterior;
    public Colonia Colonia;
    public Usuario Usuario;
    
    public Usuario getUsuario() {
        return Usuario;
    }

    public void setUsuario(Usuario Usuario) {
        this.Usuario = Usuario;
    }
    public int getidDireccion(){
        return idDireccion;
    }
    public void setidDireccion(int idDireccion){
        this.idDireccion = idDireccion;
    }
    
    public String getCalle(){
        return Calle;
    }
    
    public void setCalle(String Calle){
        this.Calle = Calle;
    }
    
    public String getNumeroInterior(){
        return NumeroInterior;
    }
    
    public void setNumeroInterior(String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    
    public String getNumeroExterior(){
        return NumeroExterior;
    }
    
    public void setNumeroExterior(String NumeroExterior){
        this.NumeroExterior = NumeroExterior;
    }

    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
}
