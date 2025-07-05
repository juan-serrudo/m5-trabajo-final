package com.tienda.dominio.repositorio;

import com.tienda.dominio.modelo.Usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Interfaz del repositorio de usuarios
 */
public interface RepositorioUsuario {
    
    /**
     * Guarda un usuario
     * @param usuario usuario a guardar
     * @return usuario guardado
     */
    Usuario guardar(Usuario usuario);
    
    /**
     * Busca un usuario por su ID
     * @param id ID del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorId(UUID id);
    
    /**
     * Busca un usuario por su email
     * @param email email del usuario
     * @return Optional con el usuario si existe
     */
    Optional<Usuario> buscarPorEmail(String email);
    
    /**
     * Obtiene todos los usuarios activos
     * @return lista de usuarios activos
     */
    List<Usuario> obtenerTodosActivos();
    
    /**
     * Obtiene todos los usuarios
     * @return lista de todos los usuarios
     */
    List<Usuario> obtenerTodos();
    
    /**
     * Elimina un usuario
     * @param id ID del usuario a eliminar
     * @return true si se eliminó correctamente
     */
    boolean eliminar(UUID id);
    
    /**
     * Verifica si un usuario existe
     * @param id ID del usuario
     * @return true si existe
     */
    boolean existe(UUID id);
    
    /**
     * Verifica si existe un usuario con el email dado
     * @param email email a verificar
     * @return true si existe
     */
    boolean existePorEmail(String email);
    
    /**
     * Autentica un usuario
     * @param email email del usuario
     * @param password contraseña del usuario
     * @return Optional con el usuario si la autenticación es exitosa
     */
    Optional<Usuario> autenticar(String email, String password);
} 