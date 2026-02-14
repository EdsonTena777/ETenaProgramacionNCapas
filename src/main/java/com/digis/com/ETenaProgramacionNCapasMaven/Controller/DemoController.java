
package com.digis.com.ETenaProgramacionNCapasMaven.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/saludo")
    public String test(@RequestParam String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    @GetMapping("/saludo/{nombre}")
    public String test2(@PathVariable("nombre") String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
    
    @GetMapping("/form")
    public String mostrarFormulario(){
        return "Formulario";
    }
    
    @PostMapping("/saludo")
    public String procesarFormulario(@RequestParam String nombre, Model model){
        model.addAttribute("nombre", nombre);
        return "HolaMundo";
    }
}
