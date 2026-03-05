
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MunicipioDAOJPAImplementation implements iMunicipioJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetByIdEstado(int idEstado){
        Result result = new Result();
        try{
            TypedQuery<Municipio> queryMunicipio = entityManager.createQuery("SELECT m FROM Municipio m WHERE m.Estado.idEstado =:idEstado", Municipio.class);
            queryMunicipio.setParameter("idEstado", idEstado);
            List<Municipio> municipioJPA = queryMunicipio.getResultList();
            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio> municipiosML = new ArrayList<>();
            for(Municipio municipio : municipioJPA){
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio municipioML = modelMapper.map(municipio, com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio.class);
                municipiosML.add(municipioML);
            }
            result.objects = (List) municipiosML;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;
    }
}
