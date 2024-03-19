package br.thiago.apiauth.shared;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LivroDTO {
    private Long id;

    private Long dono;

    private long isbn;

    private String titulo;

    private String autor;

    private Integer paginas;

    private Integer anoLancamento;

    private Integer estoque;

    private double preco;

    private String sinopse;

    private String imagem;


}
