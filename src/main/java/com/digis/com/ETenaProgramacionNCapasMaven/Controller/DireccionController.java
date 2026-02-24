
package com.digis.com.ETenaProgramacionNCapasMaven.Controller;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/direccion")
public class DireccionController {

    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;

    @GetMapping("/getbyid/{idDireccion}")
    public String getById(@PathVariable int idDireccion, Model model) {

        Result result = direccionDAOImplementation.GetById(idDireccion);

        if (!result.correct) {
            model.addAttribute("mensaje", result.errorMessage);
            return "Error";
        }

        model.addAttribute("direccion", (Direccion) result.object);
        return "DireccionGetById";
    }
    @GetMapping("/getbyid")
    @ResponseBody
    public Result getByIdAjax(@RequestParam int idDireccion) {
        return direccionDAOImplementation.GetById(idDireccion);
    }
    
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody Direccion direccion) {
        return direccionDAOImplementation.Update(direccion);
    }
    
    @PostMapping("/add")
    @ResponseBody
    public Result add(@RequestBody Direccion direccion){
        return direccionDAOImplementation.Add(direccion);
    }
}
