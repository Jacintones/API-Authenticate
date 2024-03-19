package br.thiago.apiauth.view.controllers;

import br.thiago.apiauth.services.impl.UsuarioServiceImpl;
import br.thiago.apiauth.shared.UsuarioDTO;
import br.thiago.apiauth.view.models.UsuarioRequest;
import br.thiago.apiauth.view.models.UsuarioResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl service;
    private final ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/lista")
    public ResponseEntity<List<UsuarioResponse>> obterTodos(){
        List<UsuarioDTO> usuarioDTOS = service.obterTodos();

        List<UsuarioResponse> response = usuarioDTOS
                .stream()
                .map(usuario -> modelMapper
                        .map(usuario, UsuarioResponse.class))
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<Optional<UsuarioResponse>> obterPorId(@PathVariable Long id){
        //Pegando o dto pelo email
        Optional<UsuarioDTO> dto = service.obterPorId(id);

        //Convertendo para response
        UsuarioResponse usuarioResponse = modelMapper.map(dto.get(), UsuarioResponse.class);

        //Retornando o response entity
        return new ResponseEntity<>(Optional.of(usuarioResponse), HttpStatus.OK);
    }
    /**
     * Método para salvar usuários
     * @param usuarioRequest usuário a ser salvo
     * @return
     */
    @PostMapping
    public ResponseEntity<UsuarioResponse> salvar(@RequestBody UsuarioRequest usuarioRequest) {

        //Convertendo o request pra dto
        UsuarioDTO usuarioDTO = modelMapper.map(usuarioRequest, UsuarioDTO.class);

        //Salvando no banco
        service.salvar(usuarioDTO);

        //Convertendo para response
        UsuarioResponse response = modelMapper.map(usuarioDTO, UsuarioResponse.class);

        //Retornando o Response Entity
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    @GetMapping("/admin")
    public String getAdmin(){
        return "Permissões do administrador: ";
    }

    @GetMapping("/user")
    public String getUser(){
        return "Permissões de usuário: ";
    }
}
