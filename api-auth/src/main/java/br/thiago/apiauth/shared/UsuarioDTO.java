package br.thiago.apiauth.shared;

import br.thiago.apiauth.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private RoleEnum role;

    private List<LivroDTO> livros;

}
