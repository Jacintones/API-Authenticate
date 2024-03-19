package br.thiago.apiauth.services.impl;

import br.thiago.apiauth.services.UsuarioService;
import br.thiago.apiauth.shared.UsuarioDTO;
import br.thiago.apiauth.repository.UsuarioRepository;
import br.thiago.apiauth.models.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Método para salvar usuários no banco de dados,
     * Gera-se primeiro uma instancia de usuario com os dados do parametro
     * @param usuarioDTO usuario a ser salvo
     * @return retorna o usuário salvo
     */
    @Override
    public UsuarioDTO salvar(UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(null);

        //Verificar se o usuario existe
        Optional<Usuario> usuarioJaExiste = repository.findByEmail(usuarioDTO.getEmail());

        //Se existe, lança uma exceção
        if (usuarioJaExiste.isPresent()){
            throw new RuntimeException("Usuário já existe");
        }

        //Criptografando a senha
        var passwordHash = passwordEncoder.encode(usuarioDTO.getSenha());

        //Criar a instância do usuário
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);

        //Setando a senha
        usuario.setSenha(passwordHash);

        //Salvar no banco e pegar a variável usuário atualizada com ID
        usuario = repository.save(usuario);

        //Setando o id do DTO
        usuarioDTO.setId(usuario.getId());

        //Retorna um usuárioDTO com os dados do novo usuário
        return usuarioDTO;
    }
    @Override
    public List<UsuarioDTO> obterTodos() {
        //Pega todos os usuários, mapea eles e converte para DTO
        return repository.findAll()
                .stream()
                .map(usuario -> modelMapper
                        .map(usuario, UsuarioDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<UsuarioDTO> obterPorId(Long id){
        //Pegando o usuario pelo email
        Optional<Usuario> usuario = repository.findById(id);

        if (usuario.isPresent()){
            //Convertendo para dto
            UsuarioDTO dto = modelMapper.map(usuario, UsuarioDTO.class);


            //Retornando
            return Optional.of(dto);
        }

        return Optional.empty();
    }

}
