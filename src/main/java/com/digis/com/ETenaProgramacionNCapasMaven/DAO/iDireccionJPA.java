
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;


public interface iDireccionJPA {
    Result GetById(int idDireccion);
    Result UpdateDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML);
    Result DeleteDireccion(int idDireccion);
}
