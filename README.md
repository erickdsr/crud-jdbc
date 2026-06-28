# crud-jdbc
  CRUD de usuarios criado para testar minha prática em java e POO. 

Sistema CRUD desenvolvido com Java 25 utilizando JDBC, arquitetura em camadas e integração com MySQL.

Sobre o Projeto

Este projeto foi desenvolvido com o objetivo de praticar conceitos fundamentais do desenvolvimento backend utilizando Java puro com JDBC.

A aplicação realiza operações completas de CRUD (Create, Read, Update e Delete), seguindo uma arquitetura em camadas para manter o código organizado, escalável e de fácil manutenção.

Tecnologias Utilizadas

Java 25
JDBC
MySQL
Maven
DBeaver
Git & GitHub

Arquitetura do Projeto

O projeto segue o padrão de arquitetura em camadas:
src
 ┣ main
 ┃ ┣ java
 ┃  ┣ application
 ┃  ┣ controller
 ┃  ┣ dao
 ┃  ┣ db
 ┃  ┣ dto
 ┃  ┣ exceptions
 ┃  ┣ model
 ┃  ┣ security
 ┃  ┣ service
 ┃  ┣ util
 ┃  ┗ app
 ┗ pom.xml
 
 Camadas
 
--application → Camada responsável pela configuração e inicialização da aplicação.
--controller → Responsável por controlar o fluxo da aplicação. 
--dao → Responsável pelo acesso ao banco de dados. 
--db → Responsável pela configuração e gerenciamento da conexão com o banco.
--dto → Utilizado para transportar dados entre as camadas da aplicação.
--exceptions → Responsável pelas exceções personalizadas do sistema.
--model → Entidades do sistema
--security → Responsável pela segurança da aplicação.
--service → Camada responsável pelas regras de negócio.
--util → Classes utilitárias reutilizáveis.

Como funciona o fluxo da aplicação 
    Usuário
       ↓
   Controller
       ↓
    Service
       ↓
      DAO
       ↓
 Banco de Dados

 Funcionalidades

 Cadastro de usuários
 Listagem de registros
 Atualização de dados
 Exclusão de registros
 Conexão com MySQL via JDBC
 Organização em arquitetura em camadas
 Gerenciamento de dependências com Maven

 Aprendizados

Durante o desenvolvimento deste projeto foram praticados conceitos como:

Conexão com banco utilizando JDBC
Estruturação de projetos Java
Arquitetura em camadas
Organização de código
Manipulação de SQL no Java
Gerenciamento de dependências com Maven
Versionamento com Git

 Diferenciais do Projeto
Código organizado seguindo boas práticas
Separação de responsabilidades
Estrutura semelhante a projetos reais
Fácil manutenção e escalabilidade
Base sólida para evolução com Spring Boot
