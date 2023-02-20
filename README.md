# Backend - SERVER

## Descrição

Esse é o **Backend** do projeto **SERVER**, aplicação responsavel por fazer a comunicação do Frontend 'CLIENT' com o banco de dados Postgres.


### Funcionalidades

Nessa API, há vários pacotes que serão listados e explicados quais as funções dos mesmos:


#### 1. **Config:**


*  Esse pacote é responsavel por armazenar as configurações dessa aplicação, há apenas uma classe, sendo ela:

- Classe **Beans:** Responsável por armazenar as Beans da aplicação.


#### 2. **Package Controller:**


* Essas são as controllers da API, resposaveis por manipular os dados dos endpoints e enviar para as classes services, os dados necessários para obter as informaçoes nessesárias para devolver para o frontend. Nesse pacote contem várias controllers, e cada uma delas é representada e designida a uma função da aplicação, como descrito abaixo:


1. **ClienteController:** Essá controller contém os endpoints responsável por buscar dados dos Clientes no banco de dados. Ha cinco endpoints nessa classe, que serão citados a seguir:

* Um método GET com URL http://localhost:8080/api/clientes - Endpoint responsável por buscar todos os dados da tabela Cliente.
* Um método GET com URL http://localhost:8080/api/clientes/{id} - Endpoint responsável por buscar um Cliente com um ID específico.
* Um método POST com URL http://localhost:8080/api/clientes - Endpoint responsável por inserir dados de um Cliente no banco de dados.
    O corpo da requisição para adicionar um objeto Cliente ao banco de dados deve seguir esse padrão exemplo:

      {
        "nome": "Cliente 1",
        "limiteDeCompra": 100.00,
        "diaDeFechamentoDaFatura": 20
      }

* Um método POST com URL http://localhost:8080/api/clientes - Endpoint responsável por atualizar dados de um Cliente no banco de dados.
  O corpo da requisição para adicionar um objeto Cliente ao banco de dados deve seguir esse padrão exemplo:

      {
        "id": 1,
        "nome": "Cliente 1",
        "limiteDeCompra": 100.00,
        "diaDeFechamentoDaFatura": 20
      }    

* Um método DELETE com URL http://localhost:8080/api/clientes/{id} - Endpoint responsável por deletar um Cliente com ID específico no banco de dados.



2. **PedidosController:** Essá controller contém os endpoints responsável por buscar dados dos Pedidos no banco de dados. Ha nove endpoints nessa classe, que serão citados a seguir:

* Um método GET com URL http://localhost:8080/api/pedidos - Endpoint responsável por buscar todos os dados da tabela Pedido.
* Um método GET com URL http://localhost:8080/api/pedidos/{id} - Endpoint responsável por buscar um Pedido com um ID específico.
* Um método GET com URL http://localhost:8080/api/pedidos/data/{dataStringFatura} - Endpoint responsável por a data de inicio da fatura de um cliente.
* Um método GET com URL http://localhost:8080/api/pedidos/cliente/{id} - Endpoint responsável por buscar todos os dados da tabela Pedido com cliente ID específico.
* Um método GET com URL http://localhost:8080/api/pedidos/entredatas/{dataStringInicio}/{dataStringFinal} - Endpoint responsável por buscar todos os dados da tabela .Pedido que tenham as datas dos pedidos entre as datas específicas.
* Um método GET com URL http://localhost:8080/api/pedidosprodutos/{id}" - Endpoint responsável por buscar todos os dados da tabela Pedido que contenham um produto ID específico.
* Um método POST com URL http://localhost:8080/api/pedidos - Endpoint responsável por inserir dados de um Pedido no banco de dados.
  O corpo da requisição para adicionar um objeto Pedido ao banco de dados deve seguir esse padrão exemplo:

      "{
         "cliente": {
             "id": 1
         },
         "produtosPedidos": [
            {
               "id":1
            },
            {
               "id": 2
            }
         ]
      }

* Um método POST com URL http://localhost:8080/api/pedidos - Endpoint responsável por atualizar dados de um Pedido no banco de dados.
  O corpo da requisição para atualizar um objeto Pedido ao banco de dados deve seguir esse padrão exemplo:

      "{
         "id: 1
         "cliente": {
             "id": 1
         },
         "produtosPedidos": [
            {
               "id":1
            },
            {
               "id": 2
            }
         ]
      }

* Um método DELETE com URL http://localhost:8080/api/pedidos/{id} - Endpoint responsável por deletar um Pedido com ID específico no banco de dados.


3. **ProdutosController:** Essá controller contém os endpoints responsável por buscar dados dos Produtos no banco de dados. Ha cinco endpoints nessa classe, que serão citados a seguir:


* Um método GET com URL http://localhost:8080/api/produto - Endpoint responsável por buscar todos os dados da tabela Produtos.
* Um método GET com URL http://localhost:8080/api/produto/{id} - Endpoint responsável por buscar um Produto com um ID específico.
* Um método POST com URL http://localhost:8080/api/produto - Endpoint responsável por inserir dados de um Produto Check banco de dados.
  O corpo da requisição para adicionar um objeto Produto ao banco de dados deve seguir esse padrão exemplo:

      {
         "descricao": "Produto 1",
         "preco": 10
      }

* Um método POST com URL http://localhost:8080/api/produto - Endpoint responsável por atualizar dados de um Produto no banco de dados.
  O corpo da requisição para atualizar um objeto Produto ao banco de dados deve seguir esse padrão exemplo:

      {
         "id": 1,
         "descricao": "Produto 1",
         "preco": 10
      }

* Um método DELETE com URL http://localhost:8080/api/produto/{id} - Endpoint responsável por deletar um Produto com ID específico no banco de dados.


4. **ProdutoPedidoController:** Essá controller contém os endpoints responsável por buscar dados dos ProdutoPedido no banco de dados. Ha cinco endpoints nessa classe, que serão citados a seguir:


* Um método GET com URL http://localhost:8080/api/produtopedido - Endpoint responsável por buscar todos os dados da tabela ProdutoPedido.
* Um método GET com URL http://localhost:8080/api/produtopedido/{id} - Endpoint responsável por buscar um ProdutoPedido com um ID específico.
* Um método POST com URL http://localhost:8080/api/produtopedido - Endpoint responsável por inserir dados de um ProdutoPedido no banco de dados.
  O corpo da requisição para adicionar um objeto ProdutoPedido ao banco de dados deve seguir esse padrão exemplo:

      {
         "quantidade": 1,
         "produto": {
            "id": 1
         }
      }

* Um método POST com URL http://localhost:8080/api/produtopedido - Endpoint responsável por atualizar dados de um ProdutoPedido no banco de dados.
  O corpo da requisição para atualizar um objeto ProdutoPedido ao banco de dados deve seguir esse padrão exemplo:

      {
         "id": 1,
         "quantidade": 1,
         "produto": {
            "id": 1
         }
      }

* Um método DELETE com URL http://localhost:8080/api/produtopedido/{id} - Endpoint responsável por deletar um ProdutoPedido com ID específico no banco de dados.


#### 3. **Pacote dto:**


* Esse pacote contem as classes DTOs de todas as Models da aplicação, sãp classes intermediarias que são utilizadas para transferir dados entre as camadas da aplicação, principalemnte entre as Controllers, Services e Repositories. Cada Classe na camada Model, contém uma classe DTO, para facilitar a manipulação dos dados da mesma.


#### 7. **Pacote exception:**


* Esse pacote contém todas as classes da aplicação necessárias para gerar excessoes no programa, auxiliando e evitando a possibilidade de "quebrar" a aplicação.


#### 8. **Pacote model:**


* Esse pacote contém todas as classes model da aplicação, ou seja, as classes Modelos e/ou Entidades necessárias para representar e gerenciar os dados da aplicação.


#### 9. **Package repository:**


* Esse pacote contém todas as interfaces repositories da aplicação, ou seja, essas interfaces, recebem as DTOs da camada Service, e com esses dados essa camada tem a responsabilidade de manipular e gerenciar esses dados com acesso ao banco de dados da aplicação.


#### 10. **Package service:**


* Esse pacote contém todas as classes services da aplicação, ou seja, essas classes, são as classes responsaveis pelas regras de negócios da aplicação, é nessa camada que é feito as validações dos dados e com auxilio das classes DTOs, manipular esses dados entre as camadas repositories e controllers e se necessário outras camadas.


## docker-compose


* Nesse Repositório, na pasta raiz do projeto, há um arquivo **docker-compose.yml**, que é responsável por inicializar o banco de dados Postgres, feito para essa aplicação.
