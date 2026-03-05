
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ColoniaDAOJPAImplementation implements iColoniaJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetByIdMunicipio(int idMunicipio){
        Result result = new Result();
        try{
            TypedQuery<Colonia> queryColonia = entityManager.createQuery("SELECT c FROM Colonia c WHERE c.Municipio.idMunicipio =:idMunicipio", Colonia.class);
            queryColonia.setParameter("idMunicipio", idMunicipio);
            List<Colonia> coloniaJPA = queryColonia.getResultList();
            List<com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia> coloniasML = new ArrayList<>();
            for(Colonia colonia : coloniaJPA){
                com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia coloniaML = modelMapper.map(colonia, com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia.class);
                coloniasML.add(coloniaML);
            }
            result.objects = (List) coloniasML;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
}
