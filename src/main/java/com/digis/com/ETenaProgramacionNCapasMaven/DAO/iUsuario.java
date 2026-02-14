package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario;

public interface iUsuario {
    Result GetAll();
    Result GetById(int IdUsuario);
    Result UsuarioDireccionADDSP(Usuario usuario);
}
