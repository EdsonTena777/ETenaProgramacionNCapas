
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Pais;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DireccionDAOImplementation implements iDireccion{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetById(int IdDireccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, IdDireccion);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                
                if(resultSet.next()){
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();
                    direccion.Usuario = new Usuario();
                    
                    direccion.setIdDireccion(resultSet.getInt("IDDIRECCION"));
                    direccion.setCalle(resultSet.getString("CALLE"));
                    direccion.setNumeroInterior(resultSet.getString("NUMEROINTERIOR"));
                    direccion.setNumeroExterior(resultSet.getString("NUMEROEXTERIOR"));
                    
                    direccion.Colonia.setIdColonia(resultSet.getInt("IDCOLONIA"));
                    direccion.Colonia.setNombre(resultSet.getString("NOMBRECOLONIA"));
                    direccion.Colonia.setCodigoPostal(resultSet.getString("CODIGOPOSTAL"));
                    
                    direccion.Colonia.Municipio.setIdMunicipio(resultSet.getInt("IDMUNICIPIO"));
                    direccion.Colonia.Municipio.setNombre(resultSet.getString("NOMBREMUNICIPIO"));
                    
                    direccion.Colonia.Municipio.Estado.setIdEstado(resultSet.getInt("IDESTADO"));
                    direccion.Colonia.Municipio.Estado.setNombre(resultSet.getString("NOMBREESTADO"));
                    
                    direccion.Colonia.Municipio.Estado.Pais.setIdPais(resultSet.getInt("IDPAIS"));
                    direccion.Colonia.Municipio.Estado.Pais.setNombre(resultSet.getString("NOMBREPAIS"));
                    
                    direccion.Usuario.setIdUsuario(resultSet.getInt("IDUSUARIO"));
                    
                    result.object = direccion;
                    result.correct = true;
                    
                }
                return true;
            });
            
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    @Override
    public Result Update (Direccion direccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionUpdateSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroInterior());
                callableStatement.setString(4, direccion.getNumeroExterior());
                callableStatement.setInt(5, direccion.Colonia.getIdColonia());
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
    public Result DELDireccionSP(int idDireccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionDELSP(?)}", (CallableStatementCallback<Boolean>) callableStatement-> {
                callableStatement.setInt(1, idDireccion);
                callableStatement.execute();
                return true;
            });
            result.correct=true;
        
        }catch (Exception ex){
            result.correct=false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;
    }
    
    @Override
    public Result Add(Direccion direccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionAddSP(?,?,?,?,?)}",(CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setString(1, direccion.getCalle());
                callableStatement.setString(2, direccion.getNumeroInterior());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setInt(4, direccion.Colonia.getIdColonia());
                callableStatement.setInt(5, direccion.Usuario.getIdUsuario());
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
