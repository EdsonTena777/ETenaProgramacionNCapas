
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;

public interface iDireccion {
    
    Result GetById(int IdDireccion);
    Result Update (Direccion direccion);
    Result ADDDireccionSP(Direccion direccion);
    Result DELDireccionSP(int idDireccion); 
}
