package biblioteca.biblioteca.Service;

import biblioteca.biblioteca.Dto.LivroDTO;
import biblioteca.biblioteca.Model.Autor;
import biblioteca.biblioteca.Model.Livro;
import biblioteca.biblioteca.Repository.AutorRepository;
import biblioteca.biblioteca.Repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    public List<Livro> buscarPorTermo(String termo) {
        if (termo == null || termo.isBlank()) {
            return livroRepository.findAll();
        }
        return livroRepository.findByTituloContainingIgnoreCaseOrIsbnContainingIgnoreCase(termo, termo);
    }

    public Livro salvar(LivroDTO dto) {
        Autor autor = autorRepository.findById(dto.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));

        Livro livro = Livro.builder()
                .isbn(dto.getIsbn())
                .titulo(dto.getTitulo())
                .ano(dto.getAno())
                .edicao(dto.getEdicao())
                .autor(autor)
                .build();

        return livroRepository.save(livro);
    }

    public Livro atualizar(Long id, LivroDTO dto) {
        Livro livroExistente = buscarPorId(id);
        
        if (dto.getAutorId() != null) {
            Autor autor = autorRepository.findById(dto.getAutorId())
                    .orElseThrow(() -> new RuntimeException("Autor não encontrado com ID: " + dto.getAutorId()));
            livroExistente.setAutor(autor);
        }

        livroExistente.setIsbn(dto.getIsbn());
        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setAno(dto.getAno());
        livroExistente.setEdicao(dto.getEdicao());

        return livroRepository.save(livroExistente);
    }

    public void deletar(Long id) {
        Livro livro = buscarPorId(id);
        livroRepository.delete(livro);
    }
}