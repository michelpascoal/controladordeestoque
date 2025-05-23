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

---

## 🛠️ Tecnologias Utilizadas

- Java
- NetBeans IDE
- Padrão de projeto MVC
- (Banco de dados a definir — ex: SQLite ou MySQL)

---

## 👥 Equipe de Desenvolvimento

| Nome   | Função |
|--------|--------|
| **Michel** | Modelagem / DAO |
| **Juan**   | Interface Gráfica |
| **Dudu**   | Controller / Regras de Negócio |
| **Diego**  | Banco de Dados / Conexões |
| **Caio**   | Testes / Relatórios / Documentação |

---

## 🗂️ Estrutura da Documentação

- [`documentacao/Diagrama Caso de Uso.drawio`](./documentacao/Diagrama%20Caso%20de%20Uso.drawio) — Diagrama de caso de uso (editável)
- [`documentacao/Diagrama Caso de Uso.png`](./documentacao/Diagrama%20Caso%20de%20Uso.png) — Diagrama de caso de uso (imagem)
- [`documentacao/DiagramaClasses.drawio`](./documentacao/DiagramaClasses.drawio) — Diagrama de classes (editável)
- [`documentacao/DiagramaClasses.png`](./documentacao/DiagramaClasses.png) — Diagrama de classes (imagem)

---

## 📌 Status do Projeto

🚧 *Em desenvolvimento* — estrutura do projeto e camada DAO finalizadas. Aguardando integração com interface gráfica e banco de dados.

---

## 🔄 Etapas do Desenvolvimento

- [x] Criar diagrama de classes
- [x] Iniciar estrutura do projeto no NetBeans
- [x] Implementar classes do `model` (Produto, Categoria, Movimento)
- [x] Implementar camada `DAO` com testes via `main()`
- [x] Subir projeto para o GitHub com `.gitignore`
- [ ] Implementar camada `view` (GUI - Juan)
- [ ] Implementar controladores (Dudu)
- [ ] Criar integração com banco de dados (Diego)
- [ ] Realizar testes e documentação final (Caio)

---

## 🔁 Como clonar o projeto

```bash
git clone https://github.com/michelpascoal/controladordeestoque.git
