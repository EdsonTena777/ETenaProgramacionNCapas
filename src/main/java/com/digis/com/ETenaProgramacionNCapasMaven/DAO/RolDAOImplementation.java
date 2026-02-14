
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RolDAOImplementation implements iRol{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result rolGetAll(){
        Result result = new Result();
        result.objects = new ArrayList<>(); 
        try{
            jdbcTemplate.execute("{CALL rolGetAllSP(?)}", (CallableStatementCallback<Boolean>)callableStatement -> {
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                List<Rol> roles = new ArrayList<>();
                
                while(resultSet.next()){
                    Rol rol = new Rol();
                    
                    rol.setIdRol(resultSet.getInt("idRol"));
                    rol.setNombreRol(resultSet.getString("NombreRol"));
                    roles.add(rol);
                            
                }
                result.objects = (List) roles; 
                result.correct = true;
                return true;
                    
                }
            );  
        } catch (Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return result;
    }
    
}
