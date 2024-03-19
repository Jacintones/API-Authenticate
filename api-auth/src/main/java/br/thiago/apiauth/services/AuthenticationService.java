package br.thiago.apiauth.services;

import br.thiago.apiauth.shared.AuthDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {

    String obterToken(AuthDTO authDTO);

    String validaTokenJWT(String token);

}
