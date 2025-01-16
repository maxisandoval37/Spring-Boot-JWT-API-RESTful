package ar.dev.maxisandoval.springbootjwt.service;

import ar.dev.maxisandoval.springbootjwt.models.TokenJWT;
import ar.dev.maxisandoval.springbootjwt.repository.TokenJWTRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class TokenJWTService {

    private TokenJWTRepository tokenJWTRepository;

    public void saveTokenJWT(TokenJWT tokenJWT) {
        tokenJWTRepository.save(tokenJWT);
    }

    public void saveAllTokenJWT(List<TokenJWT> tokenJWTList) {
        tokenJWTRepository.saveAll(tokenJWTList);
    }

    public List<TokenJWT> findAllValidTokenByUser(Integer id) {
        return tokenJWTRepository.findAllValidTokenByUser(id);
    }

}