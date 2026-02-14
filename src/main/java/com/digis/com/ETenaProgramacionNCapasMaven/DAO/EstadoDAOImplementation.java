
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOImplementation implements iEstado{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result estadoGetAll(){
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            jdbcTemplate.execute("{CALL estadoGetAllSP(?)}", (CallableStatementCallback<Boolean>)callableStatement ->{
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                List<Estado> estados = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    
                    estado.setIdEstado(resultSet.getInt("idEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    estados.add(estado);
                }
                result.objects = (List) estados;
                result.correct = true;
                return true;
            }
        );  
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }    
    
    @Override
    public Result GetByIdPais(int idPais){
        Result result = new Result();
        result.objects = new ArrayList<>();
        
        try{
            jdbcTemplate.execute("{CALL estadoGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, idPais);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                List<Estado> estados = new ArrayList<>();
                
                while(resultSet.next()){
                    Estado estado = new Estado();
                    estado.setIdEstado(resultSet.getInt("idEstado"));
                    estado.setNombre(resultSet.getString("Nombre"));
                    estados.add(estado);
                }
            result.objects = (List) estados;
            result.correct = true;
            return true;
        });
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
}
