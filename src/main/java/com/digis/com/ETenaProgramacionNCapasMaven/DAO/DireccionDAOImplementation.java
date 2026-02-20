
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Pais;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
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
    public Result GetByUsuarioId(int idUsuario){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionGetByUsuarioIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idUsuario);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                List<Direccion> direcciones = new ArrayList<>();
                while(resultSet.next()){
                    Direccion direccion = new Direccion();
                    direccion.Colonia = new Colonia();
                    direccion.Colonia.Municipio = new Municipio();
                    direccion.Colonia.Municipio.Estado = new Estado();
                    direccion.Colonia.Municipio.Estado.Pais = new Pais();

                    direccion.setIdDireccion(resultSet.getInt("idDireccion"));
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
                    
                    direcciones.add(direccion);
                    
                }
                result.objects = new ArrayList<Object>(direcciones);
                result.correct = true;
                return true;
            });
            
        }catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        } 
        return result;
    }
    @Override
    public Result UPDDireccionSP(Direccion direccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionUPDSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement-> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setString(4, direccion.getNumeroInterior());
                callableStatement.setInt(5, direccion.getColonia().getidColonia());
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
    public Result ADDDireccionSP(Direccion direccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionADDSP(?,?,?,?,?)}", (CallableStatementCallback<Boolean>) callableStatement-> {
                callableStatement.setInt(1, direccion.getIdDireccion());
                callableStatement.setString(2, direccion.getCalle());
                callableStatement.setString(3, direccion.getNumeroExterior());
                callableStatement.setString(4, direccion.getNumeroInterior());
                callableStatement.setInt(5, direccion.getColonia().getidColonia());
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
    public Result DELDireccionSP(Direccion direccion){
        Result result = new Result();
        try{
            jdbcTemplate.execute("{CALL DireccionDELSP(?)}", (CallableStatementCallback<Boolean>) callableStatement-> {
                callableStatement.setInt(1, direccion.getIdDireccion());
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
}
