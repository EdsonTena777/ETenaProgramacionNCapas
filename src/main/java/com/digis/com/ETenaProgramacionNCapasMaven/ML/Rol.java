
package com.digis.com.ETenaProgramacionNCapasMaven.ML;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rol {
    @JsonProperty("idRol")
    private int idRol;
    private String NombreRol;
    
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombreRol() {
        return NombreRol;
    }

    public void setNombreRol(String NombreRol) {
        this.NombreRol = NombreRol;
    }
    
    
    
}
