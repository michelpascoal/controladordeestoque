# 📦 Sistema de Controle de Estoque

Sistema desenvolvido em Java para gerenciamento e controle de estoque de produtos, com funcionalidades como cadastro, movimentação de estoque (entrada e saída), consultas e geração de relatórios.

---

## ✅ Requisitos Funcionais

- RF01: O sistema deve permitir o cadastro de produtos com nome, descrição, categoria e quantidade mínima.
- RF02: O sistema deve permitir a atualização e remoção de produtos cadastrados.
- RF03: O sistema deve permitir o cadastro de categorias.
- RF04: O sistema deve registrar movimentações de entrada e saída de produtos no estoque.
- RF05: O sistema deve validar a disponibilidade de estoque antes de registrar uma saída.
- RF06: O sistema deve emitir alertas de estoque baixo com base na quantidade mínima definida.
- RF07: O sistema deve exibir relatórios simples com as movimentações realizadas.
- RF08: O sistema deve permitir a consulta de produtos por código, nome ou categoria.

---

## ❎ Requisitos Não Funcionais

- RNF01: O sistema deve ser desenvolvido em linguagem Java.
- RNF02: A aplicação deve seguir o padrão de projeto MVC (Model-View-Controller).
- RNF03: O sistema deve ser executado localmente com interface gráfica via NetBeans.
- RNF04: O projeto deve ser versionado e hospedado no GitHub.
- RNF05: O sistema deve utilizar um banco de dados relacional (MySQL ou SQLite).
- RNF06: O código deve ser documentado seguindo padrão JavaDoc.
- RNF07: O sistema deve ter boa usabilidade e ser intuitivo para o usuário final.

---

## 🚀 Funcionalidades

- ✅ Cadastrar produtos
- 🔄 Atualizar e remover produtos
- 📊 Consultar produtos por código, nome ou categoria
- ➕ Registrar entrada de estoque
- ➖ Registrar saída de estoque
- 📄 Gerar relatórios simples (ex: estoque baixo)
- 🖥️ Interface gráfica com telas para cadastro, consulta, movimentação e menu principal

---

## 📐 Regras de Negócio

- Um produto não pode ser cadastrado com nome ou código repetido.
- A quantidade mínima de estoque é definida por produto.
- Ao registrar uma **entrada**, a quantidade em estoque aumenta.
- Ao registrar uma **saída**, o sistema verifica se há estoque suficiente.
- O sistema emite alertas quando a quantidade de um produto atinge ou fica abaixo da quantidade mínima.
- Produtos só podem ser excluídos se não houver movimentações registradas no histórico.
- Toda movimentação (entrada ou saída) é registrada com data e hora.

---

## 🛠️ Tecnologias Utilizadas

- Java 17
- Apache NetBeans IDE 26
- Maven 3.9.6
- Git + GitHub
- draw.io (para diagramas UML)
- Padrão de Projeto MVC
- Banco de dados: MySQL ou SQLite (a definir)

---

## 👥 Equipe de Desenvolvimento

| Nome Completo         | RA           | Usuário GitHub        | Função                              |
|-----------------------|--------------|-----------------------|-------------------------------------|
| Michel Pascoal        | 10724258193  | michelpascoal         | Modelagem / DAO                     |
| Juan Natan            | 10724268997  | JuanNatan             | Interface Gráfica                   |
| Eduardo Oliveira      | 10725115448  | duduprogrammer1       | Controller / Regras de Negócio      |
| Diego Oliveira        | 10725115447  | odiegoor              | Banco de Dados / Conexões           |
| Caio Roberth          | 10725114033  | caioooooo77           | Testes / Relatórios / Documentação  |

---

## 🗂️ Estrutura da Documentação

- [`documentacao/Diagrama Caso de Uso.drawio`](./documentacao/Diagrama%20Caso%20de%20Uso.drawio) — Diagrama de caso de uso (editável)
- [`documentacao/Diagrama Caso de Uso.png`](./documentacao/Diagrama%20Caso%20de%20Uso.png) — Diagrama de caso de uso (imagem)
- [`documentacao/DiagramaClasses.drawio`](./documentacao/DiagramaClasses.drawio) — Diagrama de classes (editável)
- [`documentacao/DiagramaClasses.png`](./documentacao/DiagramaClasses.png) — Diagrama de classes (imagem)

---

## 📌 Status do Projeto

✅ *Projeto concluído* — todas as etapas foram finalizadas: modelagem, DAO, interface gráfica, controladores, banco de dados, testes e documentação. O sistema está pronto e funcionando corretamente.

---

## 🔄 Etapas do Desenvolvimento

- [x] Criar diagrama de classes
- [x] Iniciar estrutura do projeto no NetBeans
- [x] Implementar classes do `model` (Produto, Categoria, Movimento)
- [x] Implementar camada `DAO` com testes via `main()`
- [x] Subir projeto para o GitHub com `.gitignore`
- [x] Implementar camada `view` (GUI - Juan)
- [x] Implementar controladores (Eduardo)
- [x] Criar integração com banco de dados (Diego)
- [x] Realizar testes e documentação final (Caio)

---

## 🔁 Como clonar o projeto

```bash
git clone https://github.com/michelpascoal/controladordeestoque.git
