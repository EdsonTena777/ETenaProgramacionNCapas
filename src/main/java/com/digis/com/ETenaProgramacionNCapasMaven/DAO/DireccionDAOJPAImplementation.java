
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import jakarta.persistence.EntityManager;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DireccionDAOJPAImplementation implements iDireccionJPA{
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Override
    public Result GetById(int IdDireccion){
        Result result = new Result();
        try{
            Direccion direccionJPA = entityManager.find(Direccion.class, IdDireccion);
            com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML = modelMapper.map(direccionJPA, com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion.class);
            result.object = direccionML;
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result UpdateDireccion(com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion direccionML){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class, direccionML.getIdDireccion());
            direccionJPA.setCalle(direccionML.getCalle());
            direccionJPA.setNumeroInterior(direccionML.getNumeroInterior());
            direccionJPA.setNumeroExterior(direccionML.getNumeroExterior());
            direccionJPA.Colonia.setIdColonia(direccionML.Colonia.getIdColonia());
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
    
    @Override
    @Transactional
    public Result DeleteDireccion(int idDireccion){
        Result result = new Result();
        try{
            com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion direccionJPA = entityManager.find(com.digis.com.ETenaProgramacionNCapasMaven.JPA.Direccion.class, idDireccion);
            entityManager.remove(direccionJPA);
            result.correct = true;
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
    return result;    
    }
}
