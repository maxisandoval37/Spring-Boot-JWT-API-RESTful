package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.models.Usuario;
import ar.dev.maxisandoval.springbootjwt.records.UsuarioResponse;
import ar.dev.maxisandoval.springbootjwt.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario saveUser(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario findUserByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<UsuarioResponse> fetchAllUsersAsResponses() {
        return usuarioRepository.findAll()
                .stream()
                .map(usuario -> new UsuarioResponse(usuario.getName(), usuario.getEmail()))
                .toList();
    }
}