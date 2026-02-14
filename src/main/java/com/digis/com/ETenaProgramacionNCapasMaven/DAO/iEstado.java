/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.digis.com.ETenaProgramacionNCapasMaven.DAO;

import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;

/**
 *
 * @author digis
 */
public interface iEstado {
    
    Result estadoGetAll();
    Result GetByIdPais(int idPais);
    
}
