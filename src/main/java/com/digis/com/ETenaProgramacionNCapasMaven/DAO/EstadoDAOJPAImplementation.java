
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EstadoDAOJPAImplementation implements iEstadoJPA{
    
    @Autowired 
    private ModelMapper modelMapper;
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public Result GetByIdPais (int idPais){
        Result result = new Result();
        try{
            TypedQuery<Estado> queryEstado = entityManager.createQuery("SELECT e FROM Estado e WHERE e.Pais.idPais =:idPais", Estado.class);
            queryEstado.setParameter("idPais", idPais);
            List<Estado> estadoJPA = queryEstado.getResultList();
            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado> estadosML = new ArrayList<>();
            for(Estado estado : estadoJPA){
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado estadoML = modelMapper.map(estado, com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado.class);
                estadosML.add(estadoML);
            }
            result.objects = (List) estadosML;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;     
    }
}
