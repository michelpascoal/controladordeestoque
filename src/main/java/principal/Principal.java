package principal;

import com.controladordeestoque.dao.CategoriaDAO;
import com.controladordeestoque.dao.ProdutoDAO;
import com.controladordeestoque.model.Categoria;
import com.controladordeestoque.model.Produto;
import java.util.Date;
import java.util.List;

public class Principal {

    public static void main(String[] args) {
        // Criando categoria
        Categoria categoria = new Categoria(1, "Bebidas");
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categoriaDAO.salvar(categoria);

        // Criando produto
        Produto produto = new Produto(1, "Refrigerante", "Lata 350ml", 50, new Date(), categoria);
        ProdutoDAO produtoDAO = new ProdutoDAO();
        produtoDAO.salvar(produto);

        // Listando produtos
        System.out.println("=== Lista de Produtos ===");
        List<Produto> produtos = produtoDAO.listarTodos();
        for (Produto p : produtos) {
            System.out.println("ID: " + p.getId());
            System.out.println("Nome: " + p.getNome());
            System.out.println("Categoria: " + p.getCategoria().getNome());
            System.out.println("Quantidade: " + p.getQuantidade());
            System.out.println("Validade: " + p.getValidade());
            System.out.println("-----------------------------");
        }
    }
}
