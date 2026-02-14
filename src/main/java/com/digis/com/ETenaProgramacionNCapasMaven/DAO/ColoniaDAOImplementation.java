
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOImplementation implements iColonia{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Override
    public Result GetByIdMunicipio(int idMunicipio){
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            jdbcTemplate.execute("{CALL ColoniaGetByIdMunicipioSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement ->{
                callableStatement.setInt(1, idMunicipio);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                List<Colonia> colonias = new ArrayList<>();
                
                while(resultSet.next()){
                    Colonia colonia = new Colonia();
                    colonia.setIdColonia(resultSet.getInt("idColonia"));
                    colonia.setNombre(resultSet.getString("Nombre"));
                    colonia.setCodigoPostal(resultSet.getString("CP"));
                    colonias.add(colonia);
                }
                result.objects = (List) colonias;
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
}
