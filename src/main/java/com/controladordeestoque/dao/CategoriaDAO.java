package com.controladordeestoque.dao;

import com.controladordeestoque.model.Categoria;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    private static List<Categoria> listaCategorias = new ArrayList<>();

    public boolean salvar(Categoria categoria) {
        if (categoria != null) {
            listaCategorias.add(categoria);
            return true;
        }
        return false;
    }

    public List<Categoria> listarTodas() {
        return listaCategorias;
    }

    public Categoria buscarPorId(int id) {
        for (Categoria c : listaCategorias) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public boolean remover(int id) {
        return listaCategorias.removeIf(c -> c.getId() == id);
    }

    public boolean atualizar(Categoria novaCategoria) {
        for (int i = 0; i < listaCategorias.size(); i++) {
            if (listaCategorias.get(i).getId() == novaCategoria.getId()) {
                listaCategorias.set(i, novaCategoria);
                return true;
            }
        }
        return false;
    }
}