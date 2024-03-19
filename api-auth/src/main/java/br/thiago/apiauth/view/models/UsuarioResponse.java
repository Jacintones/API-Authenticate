package br.thiago.apiauth.view.models;

import br.thiago.apiauth.enums.RoleEnum;
import br.thiago.apiauth.shared.LivroDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioResponse {

    private Long id;

    private String nome;

    private String email;

    private String senha;

    private RoleEnum role;

    private List<LivroDTO> livros;
}
