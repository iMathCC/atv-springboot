package biblioteca.biblioteca.Dto;

import lombok.Data;

@Data
public class LivroDTO {
    private String isbn;
    private String titulo;
    private Integer ano;
    private Integer edicao;
    private Long autorId;
}