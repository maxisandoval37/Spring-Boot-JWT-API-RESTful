package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.records.UsuarioResponse;
import ar.dev.maxisandoval.springbootjwt.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> fetchAllUsersAsResponses() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponse(usuario.getName(), usuario.getEmail()))
                .toList();
    }
}
