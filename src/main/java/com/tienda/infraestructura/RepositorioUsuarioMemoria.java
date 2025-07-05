package com.tienda.infraestructura;

import com.tienda.dominio.Usuario;
import com.tienda.dominio.RepositorioUsuario;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Implementación en memoria del repositorio de usuarios
 */
public class RepositorioUsuarioMemoria implements RepositorioUsuario {
    
    private final Map<UUID, Usuario> usuarios = new ConcurrentHashMap<>();
    private final Map<String, UUID> usuariosPorEmail = new ConcurrentHashMap<>();
    
    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(UUID.randomUUID());
        }
        usuarios.put(usuario.getId(), usuario);
        usuariosPorEmail.put(usuario.getEmail().toLowerCase(), usuario.getId());
        return usuario;
    }
    
    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return Optional.ofNullable(usuarios.get(id));
    }
    
    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return Optional.empty();
        }
        
        UUID id = usuariosPorEmail.get(email.toLowerCase());
        return Optional.ofNullable(usuarios.get(id));
    }
    
    @Override
    public List<Usuario> obtenerTodosActivos() {
        return usuarios.values().stream()
                .filter(Usuario::isActivo)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Usuario> obtenerTodos() {
        return new ArrayList<>(usuarios.values());
    }
    
    @Override
    public boolean eliminar(UUID id) {
        Usuario usuario = usuarios.get(id);
        if (usuario != null) {
            usuario.setActivo(false);
            usuarios.put(id, usuario);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean existe(UUID id) {
        return usuarios.containsKey(id);
    }
    
    @Override
    public boolean existePorEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return usuariosPorEmail.containsKey(email.toLowerCase());
    }
    
    @Override
    public Optional<Usuario> autenticar(String email, String password) {
        return buscarPorEmail(email)
                .filter(usuario -> usuario.isActivo() && 
                        password.equals(usuario.getPassword()));
    }
} 