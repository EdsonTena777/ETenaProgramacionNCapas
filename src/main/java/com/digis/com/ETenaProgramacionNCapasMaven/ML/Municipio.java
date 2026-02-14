
package com.digis.com.ETenaProgramacionNCapasMaven.ML;


public class Municipio {
    private int idMunicipio;
    private String Nombre;
    public Estado Estado;
    
    public Municipio(){
    
    }
    
    public Municipio(int idMunicipio, String Nombre, Estado Estado) {
        this.idMunicipio = idMunicipio;
        this.Nombre = Nombre;
        this.Estado = Estado;
    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }
    
    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    
}
