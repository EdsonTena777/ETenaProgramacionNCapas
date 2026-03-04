
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;


public interface iUsuarioJPA {
    Result GetAll();
    Result GetById(int idUsuario);
    Result Add(Usuario usuario);
    Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateStatus (int idUsuario, int status);
}
