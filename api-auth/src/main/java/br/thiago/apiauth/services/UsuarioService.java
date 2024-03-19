package br.thiago.apiauth.services;

import br.thiago.apiauth.shared.UsuarioDTO;

import java.util.List;

public interface UsuarioService {

    UsuarioDTO salvar(UsuarioDTO usuarioDTO);

    List<UsuarioDTO> obterTodos();
}
