# Task Management API

## Descrição

- Task Management API é uma aplicação que fornece funcionalidades para gerenciar tarefas.
- A API permite criar, atualizar, deletar e listar tarefas, além de autenticar usuários e fornecer tokens JWT.

## Requisitos

### Pré-requisitos
- Java 17 ou superior
- Maven 3.6.3 ou superior
- Docker
- Docker Compose

### Dependências
A aplicação utiliza várias bibliotecas e frameworks, incluindo:
- Spring Boot
- Spring Security
- Spring Data JPA
- Lombok
- H2 Database
- JWT
- Swagger/OpenAPI

## Execução da Aplicação

### Executar via `task.bat`

1. Clone o repositório:
   ```bash
   git clone https://github.com/leandromagela/task-management-api.git
   cd task-management-api
   ```

2. Execute o arquivo `task.bat`:
   ```bash
   .\task.bat
   ```

Este script irá compilar a aplicação, construir as imagens Docker e iniciar os contêineres.

### Arquitetura da Aplicação

A aplicação `task-management-api` segue princípios de Clean Architecture e Arquitetura Hexagonal (também conhecida como Ports and Adapters), que visam criar um sistema flexível, testável e de fácil manutenção.

#### Clean Architecture

A Clean Architecture organiza o código em camadas concêntricas, cada uma com responsabilidades específicas. O objetivo principal é manter a lógica de negócio independente de frameworks, bancos de dados, interfaces de usuário ou qualquer outro detalhe externo.

##### Camadas Principais:

1. **Entities (Entidades):**
    - Representam os objetos de domínio e encapsulam a lógica de negócio principal.
    - Exemplo: `Task.java`, `User.java`

2. **Use Cases (Casos de Uso):**
    - Contêm a lógica específica da aplicação e orquestram a interação entre as entidades.
    - Exemplo: `CreateTaskUseCase.java`, `LoginUserUseCase.java`

3. **Interface Adapters (Adaptadores de Interface):**
    - Convertem dados das camadas externas para uma forma utilizável pelas entidades e casos de uso, e vice-versa.
    - Exemplo: `AuthController.java`, `TaskController.java`

4. **Frameworks & Drivers (Frameworks e Drivers):**
    - Inclui detalhes de implementação específicos de frameworks, como controllers, repositórios e configurações.
    - Exemplo: `SecurityConfig.java`, `TokenService.java`

#### Arquitetura Hexagonal

A Arquitetura Hexagonal complementa a Clean Architecture, promovendo a separação de responsabilidades através de "portas" e "adaptadores". Isso permite que o núcleo da aplicação (casos de uso e entidades) permaneça independente de interfaces externas.

##### Componentes Principais:

1. **Core (Núcleo):**
    - Contém a lógica de negócio e é independente de detalhes de implementação.
    - Exemplo: `CreateTaskUseCase.java`, `Task.java`

2. **Ports (Portas):**
    - Interfaces que definem contratos para comunicação com o núcleo da aplicação.
    - Exemplo: `TaskRepository.java`

3. **Adapters (Adaptadores):**
    - Implementações concretas das portas, permitindo a integração com frameworks, bancos de dados, APIs externas, etc.
    - Exemplo: `TaskRepositoryImpl.java`, `CustomTaskRepositoryImpl.java`

##### Exemplo de Implementação:

- **Core:**

  `Task.java` (Entidade)
  ```java
  package com.magela.taskmanagementapi.core.model;

  public class Task {
      private Long id;
      private String description;
      private Priority priority;
      private boolean completed;
      private User user;

      // Getters and setters
  }
  ```

  `CreateTaskUseCase.java` (Caso de Uso)
  ```java
  package com.magela.taskmanagementapi.core.usecase.task;

  import com.magela.taskmanagementapi.core.model.Task;
  import com.magela.taskmanagementapi.core.repository.TaskRepository;
  import org.springframework.stereotype.Service;

  @Service
  public class CreateTaskUseCase {

      private final TaskRepository taskRepository;

      public CreateTaskUseCase(TaskRepository taskRepository) {
          this.taskRepository = taskRepository;
      }

      public Task execute(Task task) {
          return taskRepository.save(task);
      }
  }
  ```

- **Ports:**

  `TaskRepository.java` (Porta)
  ```java
  package com.magela.taskmanagementapi.core.repository;

  import com.magela.taskmanagementapi.core.model.Task;

  public interface TaskRepository {
      Task save(Task task);
  }
  ```

- **Adapters:**

  `TaskRepositoryImpl.java` (Adaptador)
  ```java
  package com.magela.taskmanagementapi.adapters.gateway;

  import com.magela.taskmanagementapi.core.model.Task;
  import com.magela.taskmanagementapi.core.repository.TaskRepository;
  import org.springframework.jdbc.core.JdbcTemplate;
  import org.springframework.stereotype.Repository;

  @Repository
  public class TaskRepositoryImpl implements TaskRepository {

      private final JdbcTemplate jdbcTemplate;

      public TaskRepositoryImpl(JdbcTemplate jdbcTemplate) {
          this.jdbcTemplate = jdbcTemplate;
      }

      @Override
      public Task save(Task task) {
          // Implementação para salvar a tarefa no banco de dados
      }
  }
  ```

Esta abordagem modular permite que diferentes partes da aplicação sejam desenvolvidas, testadas e mantidas de forma independente, promovendo uma arquitetura limpa e sustentável a longo prazo.

---

Integrando os conceitos de Clean Architecture e Arquitetura Hexagonal, a aplicação `task-management-api` é projetada para ser robusta, flexível e de fácil manutenção. Esses princípios permitem que o núcleo da aplicação permaneça independente de detalhes de implementação específicos, facilitando a adaptação a mudanças tecnológicas e requisitos de negócio.
