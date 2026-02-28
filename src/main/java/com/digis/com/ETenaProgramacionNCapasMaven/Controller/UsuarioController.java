
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
import com.digis.com.ETenaProgramacionNCapasMaven.ML.ErroresArchivo;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Estado;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Municipio;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Result;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Rol;
import com.digis.com.ETenaProgramacionNCapasMaven.ML.Usuario;
import com.digis.com.ETenaProgramacionNCapasMaven.Service.ValidationService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    
    @Autowired
    private ValidationService validationService;
    
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
    
    @PostMapping("/usuario/status")
    @ResponseBody
    public Result cambiarStatus(@RequestParam int idUsuario, @RequestParam int status){
        if(status != 0 && status!= 1){
            Result result = new Result();
            result.correct = false;
            result.errorMessage = "Status invalido";
            return result;
        }
        return usuarioDAOImplementation.UpdateStatusSP(idUsuario, status);
    }
    
    @PostMapping("/cargamasiva")
    public String CargaMasiva(@RequestParam("archivo") MultipartFile archivo, Model model, HttpSession session){
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
                    usuarios = LecturaArchivoTxt(fileDestino); 
                }else if(extension.equals("xlsx")){
                    usuarios = LecturaArchivoXLSX(fileDestino);
                }else{
                    System.out.println("extension erronea");
                }
                List<ErroresArchivo> errores = ValidarDatos(usuarios);
                if (errores.isEmpty()) {
                    model.addAttribute("errores", new ArrayList<>());
                    model.addAttribute("totalErrores", 0);
                    
                    session.setAttribute("ruta", fileDestino);
                } else {
                    model.addAttribute("errores", errores);
                    model.addAttribute("totalErrores", errores.size());
                    return "CargaMasiva";
                }

            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return "CargaMasiva";
    }
    
    @GetMapping("/cargamasiva/procesar")
    public String procesarCargaMasiva(RedirectAttributes redirectAttributes, HttpSession session) {

        Object rutaObj = session.getAttribute("ruta");
        if (rutaObj == null) {
            redirectAttributes.addFlashAttribute("mensaje", "No hay archivo pendiente por procesar.");
            return "redirect:/cargamasiva";
        }

        String rutaArchivo = rutaObj.toString();
        File archivo = new File(rutaArchivo);

        if (!archivo.exists()) {
            redirectAttributes.addFlashAttribute("mensaje", "El archivo no existe.");
            return "redirect:/cargamasiva";
        }

        String extension = rutaArchivo.substring(rutaArchivo.lastIndexOf('.') + 1).toLowerCase();

        List<Usuario> usuarios;
        if (extension.equals("txt")) {
            usuarios = LecturaArchivoTxt(archivo);
        } else if (extension.equals("xlsx")) {
            usuarios = LecturaArchivoXLSX(archivo);
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Extensión no soportada: " + extension);
            return "redirect:/cargamasiva";
        }

        Result result = usuarioDAOImplementation.AddAll(usuarios);

        if (result.correct) {
            session.removeAttribute("ruta"); 
            redirectAttributes.addFlashAttribute("mensaje", "Carga masiva insertada correctamente.");
            return "redirect:/usuario";
        } else {
            redirectAttributes.addFlashAttribute("mensaje", result.errorMessage);
            return "redirect:/cargamasiva";
        }
    }
    
    public List<Usuario> LecturaArchivoXLSX(File archivo){
        List<Usuario> usuarios = new ArrayList<>();
        Result result = new Result();
        try(InputStream inputStream = new FileInputStream(archivo);
               XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            usuarios = new ArrayList<>();
            for (Row row : sheet){
                Usuario usuario = new Usuario();
                
                usuario.setUsername(row.getCell(0).toString());
                usuario.setNombre(row.getCell(1).toString());
                usuario.setApellidoPaterno(row.getCell(2).toString());
                usuario.setApellidoMaterno(row.getCell(3).toString());
                usuario.setTelefono(formatter.formatCellValue(row.getCell(4)));
                usuario.setEmail(row.getCell(5).toString());
                usuario.setPassword(row.getCell(6).toString());
                Cell celdaFecha = row.getCell(7);
                usuario.setFechaNacimiento(celdaFecha.getDateCellValue());
                usuario.setSexo(row.getCell(8).toString());
                usuario.setCelular(formatter.formatCellValue(row.getCell(9)));
                usuario.setCURP(row.getCell(10).toString());
                usuario.Roles = new Rol();
                usuario.Roles.setIdRol(Integer.parseInt(formatter.formatCellValue(row.getCell(11))));
                Direccion direccion = new Direccion();
                direccion.setCalle(row.getCell(12).toString());
                direccion.setNumeroInterior(formatter.formatCellValue(row.getCell(13)));
                direccion.setNumeroExterior(formatter.formatCellValue(row.getCell(14)));
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(Integer.parseInt(formatter.formatCellValue(row.getCell(15))));
                usuario.getDirecciones().add(direccion);
                usuarios.add(usuario);
            }
                
        
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        return usuarios;
    }
    
    public List<Usuario> LecturaArchivoTxt(File archivo) {
        
        List<Usuario> usuarios = new ArrayList<>();
        Result result = new Result();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(archivo))){
            String linea;
            while((linea = bufferedReader.readLine()) != null){
                String[] datos = linea.split("\\|");
                Usuario usuario = new Usuario();
                usuario.setDirecciones(new ArrayList<>());
                usuario.setUsername(datos[0]);
                usuario.setNombre(datos[1]);
                usuario.setApellidoPaterno(datos[2]);
                usuario.setApellidoMaterno(datos[3]);
                usuario.setTelefono(datos[4]);
                usuario.setEmail(datos[5]);
                usuario.setPassword(datos[6]);
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                usuario.setFechaNacimiento(formato.parse(datos[7]));
                usuario.setSexo(datos[8]);
                usuario.setCelular(datos[9]);
                usuario.setCURP(datos[10]);
                usuario.Roles = new Rol();
                usuario.Roles.setIdRol(Integer.parseInt(datos[11]));
                Direccion direccion = new Direccion();
                direccion.setCalle(datos[12]);
                direccion.setNumeroInterior(datos[13]);
                direccion.setNumeroExterior(datos[14]);
                direccion.Colonia = new Colonia();
                direccion.Colonia.setIdColonia(Integer.parseInt(datos[15]));
                usuario.getDirecciones().add(direccion);
                usuarios.add(usuario);
            }
        }catch(Exception ex){
            result.correct = false;
            result.errorMessage = ex.getLocalizedMessage();
            result.ex = ex;
        }
        
        return usuarios;
    }
    public List<ErroresArchivo> ValidarDatos(List<Usuario> usuarios){
        List<ErroresArchivo> errores = new ArrayList<>();
        int fila = 0;
        for (Usuario usuario : usuarios){
            BindingResult bindingResult = validationService.ValidateObject(usuario);
            fila++;
            if(bindingResult.hasErrors()){
                for(ObjectError objectError : bindingResult.getFieldErrors()){
                    ErroresArchivo erroresArchivo = new ErroresArchivo();
                    FieldError fieldError = (FieldError) objectError;
                    erroresArchivo.setFila(fila);
                    erroresArchivo.setDato(fieldError.getField()); 
                    erroresArchivo.setDescripcion(fieldError.getDefaultMessage());
                    errores.add(erroresArchivo);
                }
            }
        }
        return errores;
    }

}   
