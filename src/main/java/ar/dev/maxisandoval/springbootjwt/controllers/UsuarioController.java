package ar.dev.maxisandoval.springbootjwt.controllers;

import ar.dev.maxisandoval.springbootjwt.records.UsuarioResponse;
import ar.dev.maxisandoval.springbootjwt.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @GetMapping("/usuarios")
    public List<UsuarioResponse> changePassword() {
        return usuarioService.fetchAllUsersAsResponses();
    }
}