# 🧸 Checkpoint 2 — Programação Spring Boot com Persistência

**FIAP — Faculdade de Informática e Administração Paulista**
**Curso:** Tecnologia em Análise e Desenvolvimento de Sistemas (TDS)
**Professor:** Dr. Marcel Stefan Wagner
 
---

## 👥 Integrantes

| Nome | RM |
|------|----|
| Maria Gabriela Landim Severo | 565146 |
| Samara Porto | 559072 |
 
---

## 📋 Descrição do Projeto

Sistema de gerenciamento de brinquedos para crianças até 14 anos, desenvolvido com **Spring Boot 3.4.5** (Maven / Java 21), conectado ao banco de dados **Oracle FIAP** via **JPA/Hibernate**.

O sistema expõe endpoints REST em `localhost:8080/brinquedos`, testados via **PowerShell (Invoke-RestMethod)** no terminal integrado do IntelliJ IDEA, contemplando CRUD completo (Create, Read, Update, Delete).
 
---

## 🏗️ Arquitetura

```
PowerShell / Navegador
      ↕  HTTP
TesteBrinquedos   (@RestController → /brinquedos)
      ↕
BrinquedoRepository   (JpaRepository → EntityManager → persist/merge/remove)
      ↕  Persist / Commit
BD Oracle FIAP → TDS_TB_BRINQUEDOS
```
 
---

## 🗂️ Estrutura de Pastas

```
checkpoint2/
├── pom.xml
├── integrantes.txt
├── README.md
└── src/main/
    ├── java/fiap/com/br/brinquedos/
    │   ├── BrinquedosApplication.java       ← classe principal (main)
    │   ├── Beans/
    │   │   ├── Brinquedo.java               ← entidade JPA (@Entity)
    │   │   └── BrinquedoRepository.java     ← repositório JPA
    │   └── Test/
    │       └── TesteBrinquedos.java         ← endpoints REST (@RestController)
    └── resources/
        ├── application.properties
        └── META-INF/persistence.xml
```
 
---

## ⚙️ Spring Initializr — Dependências

| Campo | Valor |
|-------|-------|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.4.5 |
| Group | fiap.com.br |
| Artifact | brinquedos |
| Java | 21 |

**Dependências adicionadas:**
- Spring Web
- Spring Data JPA
- Validation
- Oracle Driver (ojdbc11)
- Spring Boot DevTools
> 📸 *[<img width="800" height="380" alt="image" src="https://github.com/user-attachments/assets/5f1bb885-4996-4c9b-91e6-e1de5b137d19" />
]*
 
---

## 🗄️ Tabela Oracle: `TDS_TB_BRINQUEDOS`

| Coluna | Tipo | Descrição |
|--------|------|-----------|
| ID_BRINQUEDO | NUMBER (PK) | Identificador único — gerado via Sequence |
| NM_BRINQUEDO | VARCHAR2(100) | Nome do brinquedo |
| TP_BRINQUEDO | VARCHAR2(50) | Tipo do brinquedo |
| DS_CLASSIFICACAO | VARCHAR2(20) | Classificação etária |
| DS_TAMANHO | VARCHAR2(20) | Tamanho |
| VL_PRECO | NUMBER(10,2) | Preço |

> A tabela é criada automaticamente pelo Hibernate (`ddl-auto=update`) ao iniciar a aplicação.
 
---

## 📬 Testes CRUD — PowerShell (Terminal IntelliJ)

> Os testes foram realizados via **PowerShell** usando o comando `Invoke-RestMethod` no terminal integrado do IntelliJ IDEA, com a aplicação rodando em `localhost:8080`.
 
---

### ✅ 1. POST — Criar brinquedo (LEGO City)

**Comando executado:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/brinquedos" `
  -Method Post `
  -Body '{"nome":"LEGO City","tipo":"Educativo","classificacao":"+7 anos","tamanho":"Medio","preco":149.90}' `
  -ContentType "application/json; charset=utf-8"
```

**Resposta — 201 Created:**
```
id             : 5
nome           : LEGO City
tipo           : Educativo
classificacao  : +7 anos
tamanho        : Medio
preco          : 149,9
```

> 📸 Print do terminal:

<img width="1701" height="801" alt="image" src="https://github.com/user-attachments/assets/8dcd2f89-02ba-46b8-a726-01020902efc8" />


> ℹ️ O ID gerado foi **5** pois a Sequence do Oracle não reinicia entre tentativas anteriores — comportamento esperado.
 
---

### ✅ 2. PUT — Atualizar brinquedo (ID 1)

**Comando executado:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/brinquedos/1" `
  -Method Put `
  -Body '{"nome":"Carrinho de Controle","tipo":"Veiculo","classificacao":"8+","tamanho":"Medio","preco":79.90}' `
  -ContentType "application/json"
```

**Resposta — 200 OK:**
```
id             : 1
nome           : Carrinho de Controle
tipo           : Veiculo
classificacao  : 8+
tamanho        : Medio
preco          : 79,9
```

> 📸 Print do terminal:

<img width="854" height="224" alt="image" src="https://github.com/user-attachments/assets/db764fd2-ba54-48b0-a622-9c783915101c"/>


> ℹ️ O `save()` com ID existente usa `EntityManager.merge()` internamente, gerando um `UPDATE` no banco Oracle com Commit automático.
 
---

### ✅ 3. GET — Listar todos os brinquedos

**URL acessada no navegador:**
```
http://localhost:8080/brinquedos
```

**Resposta — 200 OK:**
```json
[
  {
    "id": 5,
    "nome": "LEGO City",
    "tipo": "Educativo",
    "classificacao": "+7 anos",
    "tamanho": "Medio",
    "preco": 149.9
  },
  {
    "id": 1,
    "nome": "Carrinho de Controle",
    "tipo": "Veiculo",
    "classificacao": "8+",
    "tamanho": "Medio",
    "preco": 99
  }
]
```

> 📸 Print do navegador:

<img width="1034" height="563" alt="image" src="https://github.com/user-attachments/assets/3cc00e93-34fc-49a4-9c5e-9fb5aa39b990" />

 
---

### ✅ 4. DELETE — Excluir brinquedo (ID 5)

**Comando executado:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/brinquedos/5" -Method Delete
```

**Resposta — 204 No Content:**
```
PS C:\Users\Administrador\Downloads\cp2_brinquedos_java>
```

> ℹ️ O terminal retorna vazio (sem corpo), pois o status **204 No Content** indica sucesso na exclusão — comportamento correto do protocolo HTTP para DELETE.

> 📸 Print do Terminal:

<img width="1794" height="593" alt="image" src="https://github.com/user-attachments/assets/bdedd26f-9ad1-4955-99c3-4744defabd82" />

 
---

### ✅ 5. GET — Listar após DELETE (confirmação)

**URL acessada no navegador:**
```
http://localhost:8080/brinquedos
```

**Resposta — 200 OK:**
```json
[
  {
    "id": 1,
    "nome": "Carrinho de Controle",
    "tipo": "Veiculo",
    "classificacao": "8+",
    "tamanho": "Medio",
    "preco": 79.9
  }
]
```

> ℹ️ O brinquedo com ID 5 (LEGO City) foi removido com sucesso do banco Oracle. Apenas o ID 1 permanece na tabela `TDS_TB_BRINQUEDOS`.

> 📸 Print do navegador:

<img width="1568" height="704" alt="image" src="https://github.com/user-attachments/assets/f6d14b6a-1bf3-4a8f-9826-63568e20379e" />
 
---

## 📦 JSONs extras para teste

```json
{ "nome": "Carrinho Hot Wheels", "tipo": "Veiculo", "classificacao": "+3 anos", "tamanho": "Pequeno", "preco": 29.90 }
```
```json
{ "nome": "Nerf Elite 2.0", "tipo": "Aventura", "classificacao": "+8 anos", "tamanho": "Medio", "preco": 119.90 }
```
```json
{ "nome": "Quebra-Cabeca 500 pecas", "tipo": "Educativo", "classificacao": "+10 anos", "tamanho": "Grande", "preco": 79.90 }
```
```json
{ "nome": "Bola de Futebol", "tipo": "Esportivo", "classificacao": "+5 anos", "tamanho": "Medio", "preco": 59.90 }
```
```json
{ "nome": "Jogo Banco Imobiliario", "tipo": "Tabuleiro", "classificacao": "+8 anos", "tamanho": "Medio", "preco": 139.90 }
```
 
---

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Função |
|------------|--------|--------|
| Java | 21 | Linguagem de programação |
| Spring Boot | 3.4.5 | Framework web |
| Spring Data JPA | 3.4.5 | Persistência / EntityManager |
| Hibernate | 6.x | ORM / Dialeto Oracle |
| Oracle JDBC (ojdbc11) | 23.x | Driver banco de dados |
| Maven | 3.8+ | Gerenciamento de dependências |
| Tomcat (embutido) | 10.x | Servidor web — porta 8080 |
| PowerShell | - | Teste dos endpoints HTTP |
 
---

*Bons estudos! 📚*
