
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

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
public class PaisDAOImplementation implements iPais{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result paisGetAll(){
        Result result = new Result();
        result.objects = new ArrayList<>(); 
        try{
            jdbcTemplate.execute("{CALL paisGetAllSP(?)}", (CallableStatementCallback<Boolean>)callableStatement -> {
                
                callableStatement.registerOutParameter(1, java.sql.Types.REF_CURSOR);
                
                callableStatement.execute();
                
                ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
                
                List<Pais> paises = new ArrayList<>();
                
                while(resultSet.next()){
                    Pais pais = new Pais();
                    
                    pais.setIdPais(resultSet.getInt("idPais"));
                    pais.setNombre(resultSet.getString("Nombre"));
                    paises.add(pais);
                            
                }
                result.objects = (List) paises; 
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
