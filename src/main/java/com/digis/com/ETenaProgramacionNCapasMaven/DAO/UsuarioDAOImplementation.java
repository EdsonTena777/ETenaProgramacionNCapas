package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.*; 



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
                        
                        direccion.setidDireccion(resultSet.getInt("idDireccion"));
                        direccion.setCalle(resultSet.getString("Calle"));
                        direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                        direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                        direccion.Colonia.setidColonia(resultSet.getInt("idColonia"));
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
                            usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                            usuario.setUsername(resultSet.getString("USERNAME"));
                            usuario.setNombre(resultSet.getString("NombreUsuario"));
                            usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                            usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                            usuario.setEmail(resultSet.getString("EMAIL"));
                            usuario.setPassword(resultSet.getString("PASSWORD"));
                            usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                            usuario.setSexo(resultSet.getString("SEXO"));
                            usuario.setTelefono(resultSet.getString("TELEFONO"));
                            usuario.setCelular(resultSet.getString("CELULAR"));
                            usuario.setCURP(resultSet.getString("CURP"));
                        
                        int idDireccion = resultSet.getInt("IDDIRECCION");
                        if(idDireccion != 0){
                            usuario.Direcciones = new ArrayList<>();
                            Direccion direccion = new Direccion();
                            direccion.Colonia = new Colonia();
                            direccion.Colonia.Municipio = new Municipio();
                            direccion.Colonia.Municipio.Estado = new Estado();
                            direccion.Colonia.Municipio.Estado.Pais = new Pais();
                            
                            direccion.setidDireccion(resultSet.getInt("idDireccion"));
                            direccion.setCalle(resultSet.getString("Calle"));
                            direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                            direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                            direccion.Colonia.setidColonia(resultSet.getInt("idColonia"));
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
                        usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                        usuario.setUsername(resultSet.getString("USERNAME"));
                        usuario.setNombre(resultSet.getString("NombreUsuario"));
                        usuario.setApellidoPaterno(resultSet.getString("APELLIDOPATERNO"));
                        usuario.setApellidoMaterno(resultSet.getString("APELLIDOMATERNO"));
                        usuario.setEmail(resultSet.getString("EMAIL"));
                        usuario.setPassword(resultSet.getString("PASSWORD"));
                        usuario.setFechaNacimiento(resultSet.getDate("FECHANACIMIENTO"));
                        usuario.setSexo(resultSet.getString("SEXO"));
                        usuario.setTelefono(resultSet.getString("TELEFONO"));
                        usuario.setCelular(resultSet.getString("CELULAR"));
                        usuario.setCURP(resultSet.getString("CURP"));
                        usuario.Direcciones = new ArrayList<>();
                    }

                    // cada fila es una dirección
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();

                    direccion.setidDireccion(resultSet.getInt("idDireccion"));
                    direccion.setCalle(resultSet.getString("Calle"));
                    direccion.setNumeroInterior(resultSet.getString("NumeroInterior"));
                    direccion.setNumeroExterior(resultSet.getString("NumeroExterior"));
                    direccion.Colonia.setidColonia(resultSet.getInt("idColonia"));
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
        
        try{jdbcTemplate.execute("{CALL UsuarioDireccionADDSP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}", (CallableStatementCallback<Boolean>)callableStatement -> {
            callableStatement.setString(1,usuario.getUsername());
            callableStatement.setString(2,usuario.getNombre());
            callableStatement.setString(3,usuario.getApellidoPaterno());
            callableStatement.setString(4, usuario.getApellidoMaterno());
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
            callableStatement.setInt(16,direccion.getColonia().getidColonia());
            
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
}