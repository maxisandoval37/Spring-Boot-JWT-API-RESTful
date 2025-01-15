package ar.dev.maxisandoval.springbootjwt.repository;

import ar.dev.maxisandoval.springbootjwt.models.TokenJWT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface TokenJWTRepository extends JpaRepository<TokenJWT, Integer> {

    @Query(value = """
      select t from TokenJWT t inner join Usuario u\s
      on t.usuario.id = u.id\s
      where u.id = :id and (t.isExpired = false or t.isRevoked = false)\s
      """)
    List<TokenJWT> findAllValidTokenByUser(Integer id);

    Optional<TokenJWT> findByToken(String token);
}