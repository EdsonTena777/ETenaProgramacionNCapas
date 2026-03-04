
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements iUsuarioJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetAll(){
        Result result = new Result();
        try{
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();
            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuarioML = usuarios.stream().map(usuarioMapper -> modelMapper.map(usuarioMapper, com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario.class)).toList();
            result.objects = (List) usuarioML;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    public Result GetById(int idUsuario){
        Result result = new Result();
        try{
            Usuario usuarioJPA = entityManager.find(Usuario.class, idUsuario);
            com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML = modelMapper.map(usuarioJPA, com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario.class);
            result.object = usuarioML;
            result.correct = true;
        }catch(Exception ex){
            result.correct=false;
            result.errorMessage=ex.getLocalizedMessage();
            result.ex=ex;
        }
    return result;    
    }
    
    @Override
    public Result Add(Usuario usuario){
        Result result = new Result();
        try{
            entityManager.persist(usuario);
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;
    }
    @Override
    @Transactional
    public Result UpdateImagen(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());
            usuarioJPA.setImagen(usuarioML.getImagen());
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result UpdateUsuario(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());
            usuarioJPA.setUsername(usuarioML.getUsername());
            usuarioJPA.setNombre(usuarioML.getNombre());
            usuarioJPA.setApellidoPaterno(usuarioML.getApellidoPaterno());
            usuarioJPA.setApellidoMaterno(usuarioML.getApellidoMaterno());
            usuarioJPA.setEmail(usuarioML.getEmail());
            usuarioJPA.setSexo(usuarioML.getSexo());
            usuarioJPA.setTelefono(usuarioML.getTelefono());
            usuarioJPA.setCelular(usuarioML.getCelular());
            usuarioJPA.setFechaNacimiento(usuarioML.getFechaNacimiento());
            usuarioJPA.Roles.setIdRol(usuarioML.Roles.getIdRol());
            result.correct=true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result UpdateStatus (int idUsuario, int status){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class,idUsuario);
            usuarioJPA.setStatus(status);
            result.correct = true;
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
}
