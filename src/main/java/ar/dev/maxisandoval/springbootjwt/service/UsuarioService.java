package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.records.UsuarioResponse;
import ar.dev.maxisandoval.springbootjwt.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponse> fetchAllUsersAsResponses() {
        return usuarioRepository.findAll()
                .stream()
                .map(user -> new UsuarioResponse(user.getName(), user.getEmail()))
                .toList();
    }
}
