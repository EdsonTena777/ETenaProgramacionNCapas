package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.*; 
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Repository 
public class UsuarioDAOImplementation implements iUsuario {
    
    
    @Autowired //inyeccion de dependencias importante
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetAll(){
        Result result = new Result();
        
        jdbcTemplate.execute("{CALL UsuarioDireccionGetAllSP(?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
            
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                result.objects = new ArrayList<>(); 
                
                while(resultSet.next()){
                    int idUsuario = resultSet.getInt("IDUSUARIO");
                    if(!result.objects.isEmpty() && idUsuario == ((Usuario)(result.objects.get(result.objects.size() -1))).getIdUsuario()){
                        
                        Direccion direccion = new Direccion();
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        
                        direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("idmunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("idEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((Usuario)(result.objects.get(result.objects.size() -1))).Direcciones.add(direccion);
                        
                          
                    } else {
                            Usuario usuario = new Usuario();
                            usuario.Roles = new Rol();
                            usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                            usuario.setUsername(resultSet.getString("USERNAME"));
                            usuario.setImagen(resultSet.getString("IMAGEN"));
                            usuario.setNombre(resultSet.getString("NombreUsuario"));
                            usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                            usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                            usuario.setEmail(resultSet.getString("EMAIL"));
                            usuario.setPassword(resultSet.getString("PASSWORD"));
                            usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                            usuario.setSexo(resultSet.getString("SEXO").trim());
                            usuario.setTelefono(resultSet.getString("TELEFONO"));
                            usuario.setCelular(resultSet.getString("CELULAR"));
                            usuario.setCURP(resultSet.getString("CURP"));
                            usuario.setStatus(resultSet.getInt("STATUS"));
                            usuario.Roles.setIdRol(resultSet.getInt("IDROL"));
                            usuario.Roles.setNombreRol(resultSet.getString("NOMBREROL"));
                        
                        int idDireccion = resultSet.getInt("IDDIRECCION");
                        if(idDireccion != 0){
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            
                            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("idmunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("idEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            
                            usuario.Direcciones.add(direccion);
                        }
                        result.objects.add(usuario);
                    }
                }
                return true;
        }); 
        return result;
    }
    @Override
    public Result GetById(int idUsuario){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL UsuarioDireccionGetByIdSP (?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setInt(1, idUsuario);
        
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);  

                callableStatement.execute();

                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                Usuario usuario = null;
                
                while(resultSet.next()){
                    if(usuario == null){ 
                        usuario = new Usuario();
                        usuario.setRoles(new Rol());
                        usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                        usuario.setUsername(resultSet.getString("USERNAME"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                        usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                        usuario.setEmail(resultSet.getString("EMAIL"));
                        usuario.setPassword(resultSet.getString("PASSWORD"));
                        usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                        usuario.setSexo(resultSet.getString("SEXO").trim());
                        usuario.setTelefono(resultSet.getString("TELEFONO"));
                        usuario.setCelular(resultSet.getString("CELULAR"));
                        usuario.setCURP(resultSet.getString("CURP"));
                        usuario.setImagen(resultSet.getString("IMAGEN"));
                        usuario.getRoles().setIdRol(resultSet.getInt("IdRol"));
                        usuario.getRoles().setNombreRol(resultSet.getString("NombreRol"));
                        usuario.Direcciones = new ArrayList<>();
                    }

                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();

                    direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                    direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                    direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("idMunicipio"));
                    direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("idEstado"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));

                    usuario.Direcciones.add(direccion);
                }
                result.object = usuario;
                result.correct = (usuario != null);
                return true;
            });
        } catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result UsuarioDireccionADDSP(Usuario usuario){
        Result result = new Result();
        
        try{jdbcTemplate.execute("{CALL UsuarioDireccionADDSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>)callableStatement -> {
            callableStatement.setString(1,usuario.getUsername());
            callableStatement.setString(2, usuario.getImagen());
            callableStatement.setString(3,usuario.getNombre());
            callableStatement.setString(4,usuario.getApellidoPaterno());
            callableStatement.setString(5, usuario.getApellidoMaterno());
            callableStatement.setString(6, usuario.getTelefono());
            callableStatement.setString(7, usuario.getEmail());
            callableStatement.setString(8, usuario.getPassword());
            
            callableStatement.setDate(9, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
            callableStatement.setString(10, String.valueOf(usuario.getSexo()));
            callableStatement.setString(11, usuario.getCelular());
            callableStatement.setString(12, usuario.getCURP());
            callableStatement.setInt(13, usuario.getRoles().getIdRol());
            
            Direccion direccion = usuario.getDirecciones().get(0);
            callableStatement.setString(14,direccion.getCalle());
            callableStatement.setString(15, direccion.getNumeroInterior());
            callableStatement.setString(16,direccion.getNumeroExterior());
            callableStatement.setInt(17,direccion.getColonia().getIdColonia());
            
            callableStatement.execute();
            
            return true;
            }
        );
            result.correct = true;
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result UPDUsuarioSP(Usuario usuario){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL UsuarioUPDSP(?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>)callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2,usuario.getUsername());
                callableStatement.setString(3,usuario.getNombre());
                callableStatement.setString(4,usuario.getApellidoPaterno());
                callableStatement.setString(5, usuario.getApellidoMaterno());
                callableStatement.setString(6, usuario.getEmail());
                callableStatement.setString(7, String.valueOf(usuario.getSexo()));
                callableStatement.setString(8, usuario.getTelefono());
                callableStatement.setString(9, usuario.getCelular());
                callableStatement.setString(10, usuario.getCURP());
                callableStatement.setDate(11, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                callableStatement.setInt(12, usuario.getRoles().getIdRol());
                callableStatement.execute();
                return true;
            });
            result.correct=true;
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result DELUsuarioSP(int idUsuario){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL UsuarioDELSP(?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idUsuario);
                callableStatement.execute();
                return true;
            });
            result.correct = true;
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;
    }
    
    @Override
    public Result UPDUsuarioImagenSP(Usuario usuario){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL UsuarioImagenUPDSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, usuario.getIdUsuario());
                callableStatement.setString(2, usuario.getImagen());
                callableStatement.execute();
                return true;
            });
            result.correct = true;
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result GetAllDinamico(String nombre, String apellidoPaterno, String apellidoMaterno, String rol){
        Result result = new Result();
        
        jdbcTemplate.execute("{CALL UsuarioDireccionGetAllDinamico(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                
                callableStatement.setString(1, nombre);
                callableStatement.setString(2, apellidoPaterno);
                callableStatement.setString(3,  apellidoMaterno);
                callableStatement.setString(4, rol);
                callableStatement.registerOutParameter(5, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(5);
                
                result.objects = new ArrayList<>(); 
                
                while(resultSet.next()){
                    int idUsuario = resultSet.getInt("IDUSUARIO");
                    if(!result.objects.isEmpty() && idUsuario == ((Usuario)(result.objects.get(result.objects.size() -1))).getIdUsuario()){
                        
                        Direccion direccion = new Direccion();
                        direccion.Colonia = new Colonia();
                        direccion.Colonia.Municipio = new Municipio();
                        direccion.Colonia.Municipio.Estado = new Estado();
                        direccion.Colonia.Municipio.Estado.Pais = new Pais();
                        
                        direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                        direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                        direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                        direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("idmunicipio"));
                        direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                        direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("idEstado"));
                        direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                        direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                        ((Usuario)(result.objects.get(result.objects.size() -1))).Direcciones.add(direccion);
                        
                          
                    } else {
                            Usuario usuario = new Usuario();
                            usuario.Roles = new Rol();
                            usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                            usuario.setUsername(resultSet.getString("USERNAME"));
                            usuario.setImagen(resultSet.getString("IMAGEN"));
                            usuario.setNombre(resultSet.getString("NombreUsuario"));
                            usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                            usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                            usuario.setEmail(resultSet.getString("EMAIL"));
                            usuario.setPassword(resultSet.getString("PASSWORD"));
                            usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                            usuario.setSexo(resultSet.getString("SEXO").trim());
                            usuario.setTelefono(resultSet.getString("TELEFONO"));
                            usuario.setCelular(resultSet.getString("CELULAR"));
                            usuario.setCURP(resultSet.getString("CURP"));
                            usuario.Roles.setIdRol(resultSet.getInt("IDROL"));
                            usuario.Roles.setNombreRol(resultSet.getString("NOMBREROL"));
                        
                        int idDireccion = resultSet.getInt("IDDIRECCION");
                        if(idDireccion != 0){
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            
                            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.Colonia.setIdColonia(resultSet.getInt("idColonia"));
                            direccion.Colonia.setNombre(resultSet.getString("NombreColonia"));
                            direccion.Colonia.setCodigoPostal(resultSet.getString("CodigoPostal"));
                            direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("idmunicipio"));
                            direccion.Colonia.Municipio.setNombre(resultSet.getString("NombreMunicipio"));
                            direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("idEstado"));
                            direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NombreEstado"));
                            direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NombrePais"));
                            
                            usuario.Direcciones.add(direccion);
                        }
                        result.objects.add(usuario);
                    }
                }
                return true;
        }); 
        return result;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result AddAll(List<Usuario> usuarios){
        Result result = new Result();
        try{
            jdbcTemplate.batchUpdate("{CALL UsuarioDireccionADDAllSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}",
                    usuarios,
                    usuarios.size(),
                    (callableStatement, usuario) -> {
                        callableStatement.setString(1, usuario.getUsername());
                        callableStatement.setString(2, usuario.getNombre());
                        callableStatement.setString(3, usuario.getApellidoPaterno());
                        callableStatement.setString(4,usuario.getApellidoMaterno());
                        callableStatement.setString(5, usuario.getTelefono());
                        callableStatement.setString(6, usuario.getEmail());
                        callableStatement.setString(7, usuario.getPassword());
                        callableStatement.setDate(8, new java.sql.Date(usuario.getFechaNacimiento().getTime()));
                        callableStatement.setString(9, String.valueOf(usuario.getSexo()));
                        callableStatement.setString(10, usuario.getCelular());
                        callableStatement.setString(11, usuario.getCURP());
                        callableStatement.setInt(12, usuario.getRoles().getIdRol());
                        Direccion direccion = usuario.getDirecciones().get(0);
                        callableStatement.setString(13,direccion.getCalle());
                        callableStatement.setString(14, direccion.getNumeroInterior());
                        callableStatement.setString(15,direccion.getNumeroExterior());
                        callableStatement.setInt(16,direccion.getColonia().getIdColonia());
                        
                    });
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result UpdateStatusSP(int idUsuario, int status){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL UsuarioStatusUPD(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idUsuario);
                callableStatement.setInt(2, status);
                callableStatement.execute();
                return true;
            });
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}