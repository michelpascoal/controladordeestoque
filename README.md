# 📦 Sistema de Controle de Estoque

Sistema desenvolvido em Java para gerenciamento e controle de estoque de produtos, com funcionalidades como cadastro, movimentação de estoque (entrada e saída), consultas e geração de relatórios.

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

## 🛠️ Tecnologias Utilizadas

- Java 17  
- Apache NetBeans IDE 26  
- Maven 3.9.6  
- Git + GitHub  
- draw.io (para diagramas UML)  
- Padrão de Projeto MVC  
- (Banco de dados a definir — ex: SQLite ou MySQL)

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

## 🧠 Regras de Negócio

- Todos os campos obrigatórios (nome, categoria, preço, quantidade) devem ser preenchidos para cadastrar um produto.  
- Não é permitido cadastrar produtos com quantidade negativa ou valor unitário igual ou menor que zero.  
- A movimentação de estoque (entrada ou saída) atualiza automaticamente a quantidade disponível do produto.  
- A quantidade não pode ser reduzida abaixo de zero ao registrar uma saída de estoque.  
- Produtos com estoque abaixo da quantidade mínima são destacados em relatórios.  
- O código do produto deve ser único e é usado como chave para buscas e operações.  
- A data de validade deve ser informada corretamente para fins de controle e pode ser usada em alertas futuros.  
- Apenas produtos cadastrados previamente podem ser movimentados ou removidos.  
- Operações críticas (como exclusão) devem exigir confirmação do usuário via interface.

---

## 📌 Status do Projeto

🚧 *Em desenvolvimento* — estrutura do projeto, camada DAO e controladores finalizados. Camada `view` já implementada. Aguardando integração com banco de dados.

---

## 🔄 Etapas do Desenvolvimento

- [x] Criar diagrama de classes  
- [x] Iniciar estrutura do projeto no NetBeans  
- [x] Implementar classes do `model` (Produto, Categoria, Movimento)  
- [x] Implementar camada `DAO` com testes via `main()`  
- [x] Subir projeto para o GitHub com `.gitignore`  
- [x] Implementar camada `view` (GUI - Juan)  
- [x] Implementar controladores (Eduardo)  
- [ ] Criar integração com banco de dados (Diego)  
- [ ] Realizar testes e documentação final (Caio)

---

## 🔁 Como clonar o projeto

```bash
git clone https://github.com/michelpascoal/controladordeestoque.git
