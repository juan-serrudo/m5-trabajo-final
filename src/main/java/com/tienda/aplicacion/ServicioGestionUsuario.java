package com.tienda.aplicacion;

import com.tienda.dominio.Usuario;
import com.tienda.dominio.RepositorioUsuario;
import com.tienda.compartido.ExcepcionNegocio;
import com.tienda.compartido.MensajesSistema;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Servicio de aplicación para la gestión de usuarios
 */
public class ServicioGestionUsuario {
    
    private final RepositorioUsuario repositorioUsuario;
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public ServicioGestionUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }
    
    /**
     * Registra un nuevo usuario
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @return usuario creado
     */
    public Usuario registrarUsuario(String nombre, String email, String password) {
        validarDatosUsuario(nombre, email, password);
        
        if (repositorioUsuario.existePorEmail(email)) {
            throw new ExcepcionNegocio(MensajesSistema.USUARIO_YA_EXISTE);
        }
        
        Usuario usuario = new Usuario(nombre, email, password);
        return repositorioUsuario.guardar(usuario);
    }
    
    /**
     * Autentica un usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @return usuario autenticado
     */
    public Usuario autenticarUsuario(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new ExcepcionNegocio(MensajesSistema.EMAIL_REQUERIDO);
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new ExcepcionNegocio("La contraseña es requerida");
        }
        
        return repositorioUsuario.autenticar(email, password)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.CREDENCIALES_INVALIDAS));
    }
    
    /**
     * Busca un usuario por ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> buscarUsuarioPorId(UUID id) {
        return repositorioUsuario.buscarPorId(id);
    }
    
    /**
     * Busca un usuario por email
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }
    
    /**
     * Obtiene todos los usuarios activos
     * @return lista de usuarios activos
     */
    public List<Usuario> obtenerUsuariosActivos() {
        return repositorioUsuario.obtenerTodosActivos();
    }
    
    /**
     * Obtiene todos los usuarios
     * @return lista de todos los usuarios
     */
    public List<Usuario> obtenerTodosLosUsuarios() {
        return repositorioUsuario.obtenerTodos();
    }
    
    /**
     * Actualiza la información de un usuario
     * @param id ID del usuario
     * @param nombre nuevo nombre
     * @param telefono nuevo teléfono
     * @param telegramId nuevo telegram ID
     * @return usuario actualizado
     */
    public Usuario actualizarUsuario(UUID id, String nombre, String telefono, String telegramId) {
        Usuario usuario = repositorioUsuario.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.USUARIO_NO_ENCONTRADO));
        
        if (nombre != null && !nombre.trim().isEmpty()) {
            usuario.setNombre(nombre);
        }
        
        usuario.setTelefono(telefono);
        usuario.setTelegramId(telegramId);
        
        return repositorioUsuario.guardar(usuario);
    }
    
    /**
     * Desactiva un usuario
     * @param id ID del usuario a desactivar
     * @return usuario desactivado
     */
    public Usuario desactivarUsuario(UUID id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.USUARIO_NO_ENCONTRADO));
        
        usuario.setActivo(false);
        return repositorioUsuario.guardar(usuario);
    }
    
    /**
     * Obtiene el historial de órdenes de un usuario
     * @param id ID del usuario
     * @return lista de órdenes del usuario
     */
    public List<com.tienda.dominio.OrdenCompra> obtenerHistorialOrdenes(UUID id) {
        Usuario usuario = repositorioUsuario.buscarPorId(id)
                .orElseThrow(() -> new ExcepcionNegocio(MensajesSistema.USUARIO_NO_ENCONTRADO));
        
        return usuario.getHistorialOrdenes();
    }
    
    /**
     * Valida los datos básicos de un usuario
     * @param nombre nombre del usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     */
    private void validarDatosUsuario(String nombre, String email, String password) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ExcepcionNegocio(MensajesSistema.NOMBRE_REQUERIDO);
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new ExcepcionNegocio(MensajesSistema.EMAIL_REQUERIDO);
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ExcepcionNegocio(MensajesSistema.EMAIL_INVALIDO);
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new ExcepcionNegocio("La contraseña es requerida");
        }
        
        if (password.length() < 6) {
            throw new ExcepcionNegocio("La contraseña debe tener al menos 6 caracteres");
        }
    }
} 