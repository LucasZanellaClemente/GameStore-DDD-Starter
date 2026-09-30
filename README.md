# GameStore — Spring Boot

## Objetivo

Completar as camadas:

- Entity
- Repository
- Service
- Controller

A infraestrutura já está preparada com:

- Java 21
- Spring Boot 3.3.4
- Spring Web
- Spring Data JPA
- Bean Validation
- Spring Security
- Swagger/OpenAPI
- H2
- Oracle JDBC
- Maven

## Executar

```bash
mvn clean spring-boot:run
```

Swagger:

`http://localhost:8080/swagger-ui.html`

OpenAPI:

`http://localhost:8080/v3/api-docs`

H2:

`http://localhost:8080/h2-console`

JDBC URL:

`jdbc:h2:mem:gamestore`

Usuário H2: `sa`

Senha H2: em branco.

## Autenticação

A API usa HTTP Basic.

Usuários:

| Usuário | Senha | Perfil |
|---|---|---|
| aluno | 123456 | USER |
| admin | admin123 | USER, ADMIN |

No Swagger, utilize o botão **Authorize** e informe usuário e senha.

## Oracle

Para executar com o perfil Oracle:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=oracle
```

Antes, ajuste `src/main/resources/application-oracle.properties`.

Exemplo:

```properties
spring.datasource.url=jdbc:oracle:thin:@oracle.fiap.com.br:1521:orcl
spring.datasource.username=RM
spring.datasource.password=Senha
```

## O desafio

As pessoas devem implementar os métodos marcados com `TODO`.

### Parte 1 — Game

Implementar:

- `POST /api/games`
- `PUT /api/games/{id}`
- `DELETE /api/games/{id}`

Regras sugeridas:

1. título obrigatório;
2. preço maior que zero;
3. estoque não pode ser negativo;
4. game inexistente deve gerar erro;
5. atualizar os dados sem criar um novo registro.

### Parte 2 — Customer

Implementar:

- `POST /api/customers`
- `PUT /api/customers/{id}`
- `DELETE /api/customers/{id}`

Regras:

1. nome obrigatório;
2. e-mail válido;
3. não permitir e-mail duplicado;
4. cliente inexistente deve gerar erro.

### Parte 3 — Order

Implementar:

- `POST /api/orders`
- `GET /api/orders`

Regra de negócio principal:

> Um pedido somente pode ser criado quando o game possui estoque suficiente.

Ao criar o pedido:

1. localizar cliente;
2. localizar game;
3. validar estoque;
4. calcular `valorTotal`;
5. reduzir estoque;
6. registrar `dataPedido`;
7. salvar pedido.

Fórmula:

`valorTotal = game.preco * quantidade`

## Desafio extra

Criar:

```http
GET /api/games/genero/{genero}
```

E implementar a consulta no Repository.

Depois criar:

```http
GET /api/orders/customer/{customerId}
```

## Critérios técnicos

A implementação deve respeitar:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Database
```

O Controller não deve conter regra de negócio.

O Repository deve ser responsável pelo acesso a dados.

O Service deve concentrar as regras de negócio.