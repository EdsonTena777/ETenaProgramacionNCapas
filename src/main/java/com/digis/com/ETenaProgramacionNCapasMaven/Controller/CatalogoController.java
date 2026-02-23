
package com.digis.com.ETenaProgramacionNCapasMaven.Controller;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController 
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private PaisDAOImplementation paisDAOImplementation;

    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;

    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;

    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;

    @GetMapping("/pais/getall")
    public Result paisGetAll() {
        return paisDAOImplementation.paisGetAll();
    }


    @GetMapping("/estado/getbyidpais")
    public Result estadoGetByIdPais(@RequestParam int idPais) {
        return estadoDAOImplementation.GetByIdPais(idPais);
    }


    @GetMapping("/municipio/getbyidestado")
    public Result municipioGetByIdEstado(@RequestParam int idEstado) {
        return municipioDAOImplementation.municipioGetById(idEstado);
    }

    @GetMapping("/colonia/getbyidmunicipio")
    public Result coloniaGetByIdMunicipio(@RequestParam int idMunicipio) {
        return coloniaDAOImplementation.GetByIdMunicipio(idMunicipio);
    }
}

