# 💳 Sistema de Gestão de Contas

API REST desenvolvida com Spring Boot para gerenciamento de contas bancárias de clientes, com funcionalidades de cadastro, edição, exclusão e consulta.

---

## 📌 Descrição

Este projeto tem como objetivo fornecer uma API robusta para gerenciar **clientes e suas contas bancárias**, incluindo operações CRUD e validações de negócio.

---

## 🛠 Tecnologias Utilizadas

- ✅ Java 17
- ✅ Spring Boot 3.2.0
- ✅ PostgreSQL
- ✅ H2 Database (para testes)
- ✅ JPA (Hibernate)
- ✅ Bean Validation
- ✅ Lombok
- ✅ Swagger (documentação de API)
- ✅ JUnit (testes)
- ✅ Maven

---

## 🚀 Funcionalidades

- 👤 CRUD de **clientes**
- 💳 CRUD de **contas**
- ✅ Validações com Bean Validation
- 🧪 Testes unitários para os serviços de clientes e contas
- 🔍 Consultas personalizadas por cliente
- 🏦 Gestão de contas com verificação de saldo e status

---

## ▶️ Como Rodar o Projeto

1. Ter o **Java 17** e o **Maven** instalados
2. Ter o **PostgreSQL** rodando e criar um banco de dados desafio
3. Clonar o repositório:
   ```bash
   git clone https://github.com/BryanLucasCabral/desafio-ras.git
   cd api
   git checkout -b dev
   ```
4. Configurar o `application.properties` ou `application.yml` com os dados do PostgreSQL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/desafio
   spring.datasource.username=postgres
   spring.datasource.password=postgres
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```
5. Rodar o projeto com o Maven:
   ```bash
   mvn spring-boot:run
   ```

6.  Rodar os testes unitários:
   ```bash
   mvn test
   ```

> Os testes cobrem os serviços de clientes e contas, validando regras de negócio, integridade dos dados e comportamento esperado dos métodos principais.

---

## 📚 Documentação Swagger

Após rodar o projeto, acesse:

```
http://localhost:8080/swagger-ui/index.html
```

---

## 🔗 Endpoints Principais

### 🧍 Clientes

| Método | Endpoint               | Ação                            |
|--------|------------------------|---------------------------------|
| POST   | `/clientes`            | 👉 Cadastrar um novo cliente    |
| PUT    | `/clientes/{id}`       | ✏️ Atualizar dados de cliente   |
| DELETE | `/clientes/{id}`       | 🗑️ Excluir cliente              |
| GET    | `/clientes`            | 📃 Listar todos os clientes     |

---

### 💳 Contas

| Método | Endpoint                           | Ação                                           |
|--------|------------------------------------|------------------------------------------------|
| POST   | `/clientes/{idCliente}/contas`     | 👉 Criar uma conta para um cliente             |
| PUT    | `/contas/{id}`                     | ✏️ Atualizar dados de uma conta                |
| DELETE | `/contas/{id}`                     | 🚫 Cancelar logicamente a conta (situação)     |
| GET    | `/clientes/{idCliente}/contas`     | 📃 Listar contas de um cliente                 |

---

## 📂 Estrutura do Projeto

```
src/
├── controller          # Controladores REST
├── service             # Regras de negócio (camada de serviço)
├── repository          # Interfaces JPA para acesso ao banco
├── model               # Entidades do banco de dados
├── dtos                # Objetos de transferência de dados
├── exception           # Tratamento de exceções customizadas
├── constants           # Constantes usadas no projeto
└── test                # Testes unitários
```

---

## 👨‍💻 Autor

**Bryan Lucas**  
📧 bryan.brasil.bl@gmail.com  
📍 Camaragibe, Pernambuco  
🔗 [LinkedIn](https://www.linkedin.com/in/bryan-nascimento-b37757209/) | [GitHub](https://github.com/BryanLucasCabral)

---

## 📃 Licença

Este projeto está sob a licença MIT. Sinta-se livre para utilizar, estudar e modificar.
