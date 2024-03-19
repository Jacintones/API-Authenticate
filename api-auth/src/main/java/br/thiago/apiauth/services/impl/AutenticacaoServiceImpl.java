package br.thiago.apiauth.services.impl;

import br.thiago.apiauth.models.Usuario;
import br.thiago.apiauth.repository.UsuarioRepository;
import br.thiago.apiauth.services.AuthenticationService;
import br.thiago.apiauth.shared.AuthDTO;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@Service
public class AutenticacaoServiceImpl implements AuthenticationService {

    @Autowired
    private UsuarioRepository repository;

    /**
     * Método para verificar como vai ser carregado, no caso, via email do usuário
     * @param email the username identifying the user whose data is required.
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).get();
    }

    /**
     * Método para obter o token do usuário
     * @param authDTO
     * @return
     */
    @Override
    public String obterToken(AuthDTO authDTO) {

        Optional<Usuario> usuario = repository.findByEmail(authDTO.email());

        return geraToken(usuario.get());
    }

    /**
     * Método para gerar o token do usuário,
     * coloca o subject, a data de expiraçao e o algoritmo
     * @param usuario usuario para poder epgar o token
     * @return
     */
    private String geraToken(Usuario usuario){
        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);

        }catch (JWTCreationException e){
            throw new RuntimeException("Erro ao gerar o token");
        }

    }

    /**
     * Método para gerar uma data de expediçao do token,
     * Pega a data atual + 8 horas e define pro horario de brasília
     * @return a data de expedição
     */
    private Instant gerarDataExpiracao() {
        return LocalDateTime.now()
                .plusHours(8)
                .toInstant(ZoneOffset.of("-03:00"));
    }

    /**
     * Método para validar o token do usuário
     * @param token token a ser validado
     * @return retorna a string do token
     */
    @Override
    public String validaTokenJWT(String token){

        try {
            Algorithm algorithm = Algorithm.HMAC256("my-secret");

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        }catch (JWTVerificationException e){
            return "";
        }
    }
}
