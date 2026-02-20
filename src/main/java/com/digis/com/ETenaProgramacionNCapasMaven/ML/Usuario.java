
package com.digis.com.ETenaProgramacionNCapasMaven.ML;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;


public class Usuario {
    
    private int idUsuario;
    @NotBlank (message = "El usuario es obligatorio")
    @Pattern (regexp = "^[a-zA-Z0-9]+$", message = "El usuario debe contener letras y numeros")
    @JsonProperty("username")
    private String Username;
    private String imagen;
    @NotBlank(message = "El nombre es obligatorio")
    @Pattern (regexp = "^[a-zA-Z ]+$", message = "El nombre solo puede contener letras")
    @JsonProperty("nombre")
    private String Nombre;
    @NotBlank(message = "El Apellido paterno es obligatorio")
    @Pattern (regexp = "^[a-zA-Z]+$", message = "El apellido paterno solo puede contener letras")
    @JsonProperty("apellidoPaterno")
    private String ApellidoPaterno;
    @Pattern (regexp = "^[a-zA-Z]+$", message = "El apellido materno solo puede contener letras")
    @JsonProperty("apellidoMaterno")
    private String ApellidoMaterno;
    @NotBlank(message = "El email es obligatoria")
    @Pattern(regexp ="^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$", message = "El correo debe tener @ y dominio")
    @JsonProperty("email")
    private String Email;
    @NotBlank(message = "La contraseña es obligatoria")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%?&])[^\\s]{8,15}$", message = "Debe tener entre 8 y 15 caracteres, una mayúscula, una minúscula, un número, un carácter especial y sin espacios")
    private String Password;
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @JsonProperty("fechaNacimiento")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date FechaNacimiento;
    @NotNull (message = "Debe seleccionar un sexo")
    @JsonProperty("sexo")
    private String Sexo;
    @NotBlank(message = "El Telefono es obligatorio")
    @Pattern (regexp = "^[0-9]+$", message = "El telefono solo puede contener numeros")
    @JsonProperty("telefono")
    private String Telefono;
    @Pattern (regexp = "^[0-9]+$", message = "El telefono solo puede contener numeros")
    @JsonProperty("celular")
    private String Celular;
    @NotBlank(message = "El CURP es obligatorio")
    @Pattern (regexp = "^[A-Z0-9]+$", message = "El CURP solo puede contener numeros y letras mayusculas")
    @JsonProperty("curp")
    private String CURP;
    @JsonProperty("roles")
    public Rol Roles;
    public List<Direccion> Direcciones;
    
    public Usuario() {
        this.Direcciones = new ArrayList<>();
}

    public Usuario(int idUsuario, String Username, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, String Password, Date FechaNacimiento, String Sexo, String Telefono, String Celular, String imagen, String CURP, Rol Roles, List<Direccion> Direcciones) {
        this.idUsuario = idUsuario;
        this.Username = Username;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.imagen = imagen;
        this.CURP = CURP;
        this.Roles = Roles;
        this.Direcciones = Direcciones;
    }
    
    public String getUsername(){
        return Username;
    }
    
    public void setUsername (String Username){
        this.Username = Username;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getNombre (){
        return Nombre;
    }
    public void setNombre (String Nombre) {
        this.Nombre = Nombre;
    }
    
    public String getApellidoPaterno() {
        return ApellidoPaterno;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno ){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    public void setApellidoMaterno (String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    
    public String getEmail(){
        return Email;
    }
    public void setEmail (String Email){
        this.Email = Email;
    }
    
    public String getPassword(){
        return Password;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }

    public Date getFechaNacimiento() {
        return FechaNacimiento;
    }

    public void setFechaNacimiento(Date FechaNacimiento) {
        this.FechaNacimiento = FechaNacimiento;
    }    
    
    public String getSexo(){
        return Sexo;
    }
    
    public void setSexo(String Sexo){
        this.Sexo = Sexo;
    }
    
    public String getTelefono (){
        return Telefono;
    }
    
    public void setTelefono (String Telefono){
        this.Telefono = Telefono;
    }
    
    public String getCelular (){
        return Celular;
    }
    
    public void setCelular (String Celular){
        this.Celular = Celular;
    }
    
    public String getCURP (){
        return CURP;
    }
    
    public void setCURP(String CURP){
        this.CURP = CURP;
    }

    public Rol getRoles() {
        return Roles;
    }

    public void setRoles(Rol Roles) {
        this.Roles = Roles;
    }

    public List<Direccion> getDirecciones() {
        return Direcciones;
    }

    public void setDirecciones(List<Direccion> Direcciones) {
        this.Direcciones = Direcciones;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    
    
}
