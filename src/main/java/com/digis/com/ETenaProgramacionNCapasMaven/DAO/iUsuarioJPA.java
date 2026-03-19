
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import java.util.List;


public interface iUsuarioJPA {
    Result GetAll();
    Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol);
    Result GetById(int idUsuario);
    Result Add(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result AddAll(List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML);
    Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result UpdateStatus(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result Delete(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML);
    Result GetByUsername(String Username);
}
