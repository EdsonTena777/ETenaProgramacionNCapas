
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOImplementation implements iMunicipio {
    
@Autowired
private JdbcTemplate jdbcTemplate;

    @Override
    public Result municipioGetById(int idEstado){
        Result result = new Result();
        result.objects = new ArrayList<>();
        try{
            jdbcTemplate.execute("{CALL municipioGetByIdSP(?,?)}", (CallableStatementCallback<Boolean>) callableStatement -> {
                callableStatement.setInt(1, idEstado);
                callableStatement.registerOutParameter(2, java.sql.Types.REF_CURSOR);
                callableStatement.execute();
                ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
                List<Municipio> municipios = new ArrayList<>();
                
                while(resultSet.next()){
                    Municipio municipio = new Municipio();
                    municipio.setIdMunicipio(resultSet.getInt("idMunicipio"));
                    municipio.setNombre(resultSet.getString("Nombre"));
                    municipios.add(municipio);
                }
            result.objects = (List) municipios;
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
