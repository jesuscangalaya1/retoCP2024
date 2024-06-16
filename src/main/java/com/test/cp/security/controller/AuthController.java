package com.test.cp.security.controller;

import com.test.cp.security.dto.JwtDto;
import com.test.cp.security.dto.LoginUsuario;
import com.test.cp.security.dto.Mensaje;
import com.test.cp.security.dto.NuevoUsuario;
import com.test.cp.security.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "LOGIN", description = "Operaciones permitidas sobre la entidad Pedido")
public class AuthController {

    private final UsuarioService usuarioService;

    @Operation(summary = "Crear un nuevo usuario",
            description = "Crear un nuevo usuario para logeart y obtener un token de autenticación, copiar bien el token generado para poder usarlo en las demás peticiones.")
    @PostMapping("/nuevo")
    public ResponseEntity<Mensaje> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario) {
        return ResponseEntity.ok(usuarioService.save(nuevoUsuario));
    }

    @Operation(summary = "Iniciar sesión ´JWT´",
            description = "Devuelve un token de autenticación")
    @PostMapping("/login")
    public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario) {
        return ResponseEntity.ok(usuarioService.login(loginUsuario));
    }

}