
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Rol;
import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.query.QueryEnhancerFactories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UsuarioDAOJPAImplementation implements iUsuarioJPA {
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetByUsername(String username) {
        Result result = new Result();

        try {
            TypedQuery<com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario> queryUsuario =
                    entityManager.createQuery(
                            "FROM Usuario WHERE Username = :pUsername",
                            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class
                    );

            queryUsuario.setParameter("pUsername", username);

            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = queryUsuario.getSingleResult();

            com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML =
                    new com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario();

            usuarioML.setUsername(usuarioJPA.getUsername());
            usuarioML.setPassword(usuarioJPA.getPassword());
            usuarioML.setStatus(usuarioJPA.getStatus());

            if (usuarioJPA.getRoles() != null) {
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol rolML =
                        new com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol();

                rolML.setNombreRol(usuarioJPA.getRoles().getNombreRol());
                usuarioML.setRoles(rolML);
            }

            result.object = usuarioML;
            result.correct = true;


        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }

        return result;
    }
    
    @Override
    public Result GetAll() {
        Result result = new Result();
        try {
            TypedQuery<Usuario> queryUsuario = entityManager.createQuery("FROM Usuario", Usuario.class);
            List<Usuario> usuarios = queryUsuario.getResultList();

            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML = new ArrayList<>();

            for (Usuario usuarioJPA : usuarios) {
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML =
                        new com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario();

                usuarioML.setIdUsuario(usuarioJPA.getIdUsuario());
                usuarioML.setUsername(usuarioJPA.getUsername());
                usuarioML.setImagen(usuarioJPA.getImagen());
                usuarioML.setNombre(usuarioJPA.getNombre());
                usuarioML.setApellidoPaterno(usuarioJPA.getApellidoPaterno());
                usuarioML.setApellidoMaterno(usuarioJPA.getApellidoMaterno());
                usuarioML.setEmail(usuarioJPA.getEmail());
                usuarioML.setPassword(usuarioJPA.getPassword());
                usuarioML.setFechaNacimiento(usuarioJPA.getFechaNacimiento());
                usuarioML.setSexo(usuarioJPA.getSexo());
                usuarioML.setTelefono(usuarioJPA.getTelefono());
                usuarioML.setCelular(usuarioJPA.getCelular());
                usuarioML.setCURP(usuarioJPA.getCURP());
                usuarioML.setStatus(usuarioJPA.getStatus());

                if (usuarioJPA.getRoles() != null) {
                    com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol rolML =
                            new com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol();

                    rolML.setIdRol(usuarioJPA.getRoles().getIdRol());
                    rolML.setNombreRol(usuarioJPA.getRoles().getNombreRol());

                    usuarioML.setRoles(rolML);
                }

                List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion> direccionesML = new ArrayList<>();

                if (usuarioJPA.getDirecciones() != null) {
                    for (com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA : usuarioJPA.getDirecciones()) {
                        com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML =
                                new com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion();

                        direccionML.setIdDireccion(direccionJPA.getIdDireccion());
                        direccionML.setCalle(direccionJPA.getCalle());
                        direccionML.setNumeroInterior(direccionJPA.getNumeroInterior());
                        direccionML.setNumeroExterior(direccionJPA.getNumeroExterior());

                        if (direccionJPA.getColonia() != null) {
                            com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia coloniaML =
                                    new com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia();

                            coloniaML.setIdColonia(direccionJPA.getColonia().getIdColonia());
                            coloniaML.setNombre(direccionJPA.getColonia().getNombre());
                            coloniaML.setCodigoPostal(direccionJPA.getColonia().getCodigoPostal());

                            if (direccionJPA.getColonia().getMunicipio() != null) {
                                com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio municipioML =
                                        new com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio();

                                municipioML.setIdMunicipio(direccionJPA.getColonia().getMunicipio().getIdMunicipio());
                                municipioML.setNombre(direccionJPA.getColonia().getMunicipio().getNombre());

                                if (direccionJPA.getColonia().getMunicipio().getEstado() != null) {
                                    com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado estadoML =
                                            new com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado();

                                    estadoML.setIdEstado(direccionJPA.getColonia().getMunicipio().getEstado().getIdEstado());
                                    estadoML.setNombre(direccionJPA.getColonia().getMunicipio().getEstado().getNombre());

                                    municipioML.setEstado(estadoML);
                                }

                                coloniaML.setMunicipio(municipioML);
                            }

                            direccionML.setColonia(coloniaML);
                        }

                        direccionesML.add(direccionML);
                    }
                }

                usuarioML.setDirecciones(direccionesML);

                usuariosML.add(usuarioML);
            }

            result.objects = (List) usuariosML;
            result.correct = true;

        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol) {
        Result result = new Result();
        try {
            StringBuilder jpql = new StringBuilder("SELECT u FROM Usuario u LEFT JOIN u.Roles r WHERE 1=1 ");

            if (nombre != null && !nombre.isEmpty()) jpql.append("AND LOWER(u.Nombre) LIKE :nombre ");
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) jpql.append("AND LOWER(u.ApellidoPaterno) LIKE :apellidoPaterno ");
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) jpql.append("AND LOWER(u.ApellidoMaterno) LIKE :apellidoMaterno ");
            if (rol != null && !rol.isEmpty()) jpql.append("AND LOWER(r.NombreRol) LIKE :rol ");

            TypedQuery<Usuario> queryUsuario = entityManager.createQuery(jpql.toString(), Usuario.class);

            if (nombre != null && !nombre.isEmpty()) queryUsuario.setParameter("nombre", "%" + nombre.toLowerCase() + "%");
            if (apellidoPaterno != null && !apellidoPaterno.isEmpty()) queryUsuario.setParameter("apellidoPaterno", "%" + apellidoPaterno.toLowerCase() + "%");
            if (apellidoMaterno != null && !apellidoMaterno.isEmpty()) queryUsuario.setParameter("apellidoMaterno", "%" + apellidoMaterno.toLowerCase() + "%");
            if (rol != null && !rol.isEmpty()) queryUsuario.setParameter("rol", "%" + rol.toLowerCase() + "%");

            List<Usuario> usuarios = queryUsuario.getResultList();

            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuarioML = usuarios.stream().map(usuarioMapper -> modelMapper.map(usuarioMapper, com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario.class)).toList();

            result.objects = (List) usuarioML;
            result.correct = true;
        } catch (Exception ex) {
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
    @Transactional
    public Result Add(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = modelMapper.map(usuarioML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class);
            usuarioJPA.setRoles(entityManager.find(Rol.class, usuarioML.getRoles().getIdRol()));
            if(usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()){
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML = usuarioML.getDirecciones().get(0);
                com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = modelMapper.map(direccionML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class);
                direccionJPA.setColonia(entityManager.find(Colonia.class, direccionML.getColonia().getIdColonia()));
                direccionJPA.setUsuario(usuarioJPA);
                if(usuarioJPA.getDirecciones() == null){
                    usuarioJPA.setDirecciones(new ArrayList<>());
                }
                usuarioJPA.getDirecciones().add(direccionJPA);
            }
            entityManager.persist(usuarioJPA);
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
    public Result AddAll(List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario> usuariosML){
        Result result = new Result();
        try{
            for(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML : usuariosML){
                com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = modelMapper.map(usuarioML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class);
                usuarioJPA.setRoles(entityManager.find(Rol.class, usuarioML.getRoles().getIdRol()));
                if(usuarioML.getDirecciones() != null && !usuarioML.getDirecciones().isEmpty()){
                    com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML = usuarioML.getDirecciones().get(0);
                    com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = modelMapper.map(direccionML, com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class);
                    direccionJPA.setColonia(entityManager.find(Colonia.class, direccionML.getColonia().getIdColonia()));
                    direccionJPA.setUsuario(usuarioJPA);
                    if(usuarioJPA.getDirecciones() == null){
                        usuarioJPA.setDirecciones(new ArrayList<>());
                    }
                    usuarioJPA.getDirecciones().add(direccionJPA);
                }
                entityManager.persist(usuarioJPA);
                result.correct = true;
            }
        
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
            if (usuarioJPA != null) {
                usuarioJPA.setImagen(usuarioML.getImagen());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe en la base de datos.";
            }
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
            Rol rolJPA = entityManager.find(Rol.class, usuarioML.getRoles().getIdRol());
            usuarioJPA.setRoles(rolJPA);
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
    public Result UpdateStatus(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();
        try {
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = 
                entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA != null) {
                usuarioJPA.setStatus(usuarioML.getStatus());
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe en la base de datos.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

    @Override
    @Transactional
    public Result Delete(com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario usuarioML) {
        Result result = new Result();
        try {
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario usuarioJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Usuario.class, usuarioML.getIdUsuario());

            if (usuarioJPA != null) {
                entityManager.remove(usuarioJPA);
                result.correct = true;
            } else {
                result.correct = false;
                result.errorMessage = "El usuario no existe.";
            }
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }

}
