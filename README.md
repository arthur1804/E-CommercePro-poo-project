# draft-poo
Projeto pra passar de ano ai

esqueleto:
src/
|-- com/
|   |-- seuprojeto/
|       |-- entidades/
|           |-- Usuario.java
|           |-- Loja.java
|           |-- Produto.java
|           |-- Carrinho.java
|           |-- ItemCarrinho.java
|           |-- Compra.java
|       |-- dao/
|           |-- UsuarioDAO.java
|           |-- LojaDAO.java
|           |-- ProdutoDAO.java
|           |-- CarrinhoDAO.java
|           |-- ItemCarrinhoDAO.java
|           |-- CompraDAO.java
|       |-- Main.java

Entidades:
Eu criei a classe Usuario com atributos como nome, email e senha, implementando métodos para autenticação e alteração de senha. Na Loja, adiciono atributos como nome da loja e endereço, criando métodos para adicionar/remover produtos e gerenciar o estoque. Em Produto, acrescento atributos como nome, preço e quantidade em estoque. Carrinho está relacionado ao usuário, permitindo adicionar/remover itens. ItemCarrinho está associado a um produto e inclui quantidade, preço unitário, etc. Para Compra, faço a associação com usuário e itens de carrinho, criando métodos para calcular o total da compra e verificar o status.

DAO (Data Access Object):
Para UsuarioDAO, implemento métodos para inserir, atualizar, buscar e excluir usuários no banco de dados. O mesmo para LojaDAO, ProdutoDAO, CarrinhoDAO, ItemCarrinhoDAO e CompraDAO.

Não impplementei completamente as funções na interface gráfica devido ao pouco tempo para entrega.
