
package com.digis.com.ETenaProgramacionNCapasMaven.Controller;

import com.digis.com.ETenaProgramacionNCapasMaven.DAO.ColoniaDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.DireccionDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.EstadoDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.MunicipioDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.PaisDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.RolDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.DAO.UsuarioDAOImplementation;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Colonia;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Direccion;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping
public class UsuarioController {
    
    @Autowired
    private UsuarioDAOImplementation usuarioDAOImplementation;
    
    @Autowired 
    private PaisDAOImplementation paisDAOImplementation;
    
    @Autowired
    private RolDAOImplementation rolDAOImplementation;
    
    @Autowired
    private EstadoDAOImplementation estadoDAOImplementation;
    
    @Autowired
    private MunicipioDAOImplementation municipioDAOImplementation;
    
    @Autowired
    private ColoniaDAOImplementation coloniaDAOImplementation;
    
    @Autowired
    private DireccionDAOImplementation direccionDAOImplementation;
    
    @GetMapping("/usuario")
    public String index(Model model){
        Result result = usuarioDAOImplementation.GetAll();
        
        model.addAttribute("usuarios", result.objects);
        
        return "GetAll";
    }
    
    @PostMapping("/buscar") 
    public String buscar( @RequestParam(required = false) String nombre, @RequestParam(required = false) String apellidoPaterno, @RequestParam(required = false) String apellidoMaterno, @RequestParam(required = false) String rol, Model model) {
        Result result = usuarioDAOImplementation.GetAllDinamico( nombre, apellidoPaterno, apellidoMaterno, rol);
        model.addAttribute("usuarios", result.objects); 
        return "GetAll"; // reutiliza la misma vista para mostrar resultados
    }
    
    @GetMapping("/GetById")
    public String GetById(@RequestParam int idUsuario, Model model){
        
        Result result = usuarioDAOImplementation.GetById(idUsuario);
        
        model.addAttribute("usuario", result.object);
        
        return "GetById";
    }
    @GetMapping("/UsuarioDetail")
        public String UsuarioDetail(@RequestParam int idUsuario,Model model){

        
        Result resultUsuario = usuarioDAOImplementation.GetById(idUsuario);
        Result resultRol = rolDAOImplementation.rolGetAll();
        Result resultPaises = paisDAOImplementation.paisGetAll();
        Result resultEstados = estadoDAOImplementation.estadoGetAll();
        
        Usuario usuario = (Usuario) resultUsuario.object;
        

        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", resultRol.objects);
        model.addAttribute("paises", resultPaises.objects);
        model.addAttribute("estados", resultEstados.objects);

        return "UsuarioDetail";
    }
        
    @GetMapping("/form")
    public String form(Model model){
        Usuario usuario = new Usuario();
        Direccion direccion = new Direccion();
        direccion.setColonia(new Colonia());
        usuario.setRoles(new Rol()); 
        usuario.setDirecciones(new ArrayList<>());
        usuario.getDirecciones().add(direccion);
        
        Result resultPaises = paisDAOImplementation.paisGetAll();
        Result resultRoles = rolDAOImplementation.rolGetAll();
        Result resultEstados = estadoDAOImplementation.estadoGetAll();
        
        model.addAttribute("estados", resultEstados.objects);
        model.addAttribute("roles", resultRoles.objects);
        model.addAttribute("usuario", usuario);
        model.addAttribute("paises", resultPaises.objects);
        return "Formulario";
    }   
    @PostMapping("/form")
    public String form(@Valid @ModelAttribute("usuario") Usuario usuario, BindingResult bindingResult, @RequestParam("imagenFile") MultipartFile imagenFile, Model model){
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> {
            System.out.println(error.getDefaultMessage());
            });
            Result resultPaises = paisDAOImplementation.paisGetAll();
            Result resultEstados = estadoDAOImplementation.estadoGetAll();
            Result resultRoles = rolDAOImplementation.rolGetAll();
            
            model.addAttribute("paises", resultPaises.objects);
            model.addAttribute("estados", resultEstados.objects);
            model.addAttribute("roles", resultRoles.objects);
            
            if (usuario.getDirecciones() == null || usuario.getDirecciones().isEmpty()) {
                Direccion direccion = new Direccion();
                Colonia colonia = new Colonia();
                Municipio municipio = new Municipio();
                Estado estado = new Estado();

                municipio.setEstado(estado);
                colonia.setMunicipio(municipio);
                direccion.setColonia(colonia);

                usuario.setDirecciones(new ArrayList<>());
                usuario.getDirecciones().add(direccion);
            }
            
            int idEstado = usuario.getDirecciones().get(0)
                              .getColonia()
                              .getMunicipio()
                              .getEstado()
                              .getIdEstado();

            if(idEstado != 0){
                Result resultMunicipios =
                    municipioDAOImplementation.municipioGetById(idEstado);
                model.addAttribute("municipios", resultMunicipios.objects);
            }else{
                model.addAttribute("municipios", new ArrayList<>());
            }


            int idMunicipio = usuario.getDirecciones().get(0)
                                     .getColonia()
                                     .getMunicipio()
                                     .getIdMunicipio();

            if(idMunicipio != 0){
                Result resultColonias =
                    coloniaDAOImplementation.GetByIdMunicipio(idMunicipio);
                model.addAttribute("colonias", resultColonias.objects);
            }else{
                model.addAttribute("colonias", new ArrayList<>());
            }
            return "Formulario";
            }
       
            if (imagenFile != null && !imagenFile.isEmpty()) {
                String nombreArchivo = imagenFile.getOriginalFilename();
                String extension = nombreArchivo.substring(nombreArchivo.lastIndexOf(".") + 1).toLowerCase();

            try {
                if (extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
                    byte[] bytes = imagenFile.getBytes();
                    String imagenBase64 = java.util.Base64.getEncoder().encodeToString(bytes);
                    usuario.setImagen(imagenBase64); 
                    System.out.println("Imagen procesada correctamente");
                } else {
                    System.out.println("Formato no permitido: " + extension);
                    
                }
            } catch (Exception ex) {
                return "Formulario";
            }
            } else {
                System.out.println("Se conserva la imagen");
            }

        System.out.println("Ejecutando procedimiento de guardado...");
        usuarioDAOImplementation.UsuarioDireccionADDSP(usuario);
        return "redirect:/usuario";
    }
    @PostMapping("/usuario/updateImagen")
    @ResponseBody
    public boolean UpdateImagen(@RequestBody Usuario usuario){
        Result result = usuarioDAOImplementation.UPDUsuarioImagenSP(usuario);
        return result.correct;
    }
    @PostMapping("/usuario/update")
    @ResponseBody
    public Result updateUsuario(@RequestBody Usuario usuario){
        return usuarioDAOImplementation.UPDUsuarioSP(usuario);
    }
    
    @GetMapping("/usuario/estados/{idPais}")
    @ResponseBody
    public Result GetByIdPais(@PathVariable int idPais){
        return estadoDAOImplementation.GetByIdPais(idPais);
    }
    
    @GetMapping("/usuario/municipios/{idEstado}")
    @ResponseBody
    public Result municipioGetById(@PathVariable int idEstado){
        return municipioDAOImplementation.municipioGetById(idEstado);
    }
    
    @GetMapping("/usuario/colonias/{idMunicipio}")
    @ResponseBody
    public Result GetByIdMunicipio(@PathVariable int idMunicipio){
        return coloniaDAOImplementation.GetByIdMunicipio(idMunicipio);
    }
    @PostMapping("/usuario/delete")
    @ResponseBody
    public Result DelUsaurioId(@RequestParam int idUsuario){
        return usuarioDAOImplementation.DELUsuarioSP(idUsuario);
    }
    @GetMapping("/direccion/delete/{idDireccion}")
    @ResponseBody
    public Result DelDireccionId(@PathVariable int idDireccion){
        return direccionDAOImplementation.DELDireccionSP(idDireccion);   
    }
    @GetMapping("/cargamasiva")
    public String CargaMasiva() {
        return "CargaMasiva";
    }
    
    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo){
        Result result = new Result();
        try{
            if (archivo != null && !archivo.isEmpty()){
                String rutaBase = System.getProperty("user.dir");
                String rutaCarpeta = "src/main/resources/archivosCM";
                String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmSS"));  
                String nombreArchivo = fecha + archivo.getOriginalFilename();
                String rutaArchivo = rutaBase + "/" + rutaCarpeta + "/" + nombreArchivo;
                String extension = archivo.getOriginalFilename().split("\\.")[1];
                File fileDestino = new File(rutaArchivo);
                archivo.transferTo(fileDestino);
                List<Usuario> usuarios = null;
                
                if(extension.equals("txt")){
                    System.out.println(extension);
                    usuarios = LecturaArchivoTxt(fileDestino); 
                }else if(extension.equals("xlsx")){

                }else{
                    System.out.println("extension erronea");
                }
                result.correct = true;  
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "CargaMasiva";
    }
    
    public List<Usuario> LecturaArchivoTxt(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        Result result = new Result();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                String[] datos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setTelefono(datos[4]);
                usuario.setEmail(datos[5]);
                usuario.setPassword(datos[6]);
                usuarios.add(usuario); 
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return usuarios;
    }
    
}   
