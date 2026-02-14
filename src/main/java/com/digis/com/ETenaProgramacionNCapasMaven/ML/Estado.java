
package com.digis.com.ETenaProgramacionNCapasMaven.ML;

public class Estado {
    
    private int idEstado;
    private String Nombre;
    public Pais Pais;

    public Estado() {
    }

    public Estado(int idEstado, String Nombre) {
        this.idEstado = idEstado;
        this.Nombre = Nombre;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais Pais) {
        this.Pais = Pais;
    }

    
}
