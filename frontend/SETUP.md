# Sistema de Leilão - Configuração do Frontend

## 📋 Visão Geral

Este projeto implementa o frontend de um sistema de leilão com autenticação completa utilizando React e integração com API backend via Axios.

## 🚀 Funcionalidades Implementadas

### Autenticação
- ✅ Login com email e senha
- ✅ Armazenamento seguro de token JWT no localStorage
- ✅ Gerenciamento de estado de autenticação com React Context
- ✅ Rotas protegidas que requerem autenticação
- ✅ Logout automático quando token expira
- ✅ Recuperação de senha (integrada com backend)

### Configuração do Axios
- ✅ Interceptor para adicionar token automaticamente nas requisições
- ✅ Interceptor para tratar erros de autenticação (401)
- ✅ Configuração de timeout e headers padrão
- ✅ Variáveis de ambiente para configuração da API

## 🛠️ Tecnologias Utilizadas

- **React** 19.1.1
- **React Router Dom** 6.28.0
- **Axios** (para requisições HTTP)
- **Context API** (gerenciamento de estado)
- **localStorage** (armazenamento de token)

## 📁 Estrutura do Projeto

```
frontend/
├── src/
│   ├── components/          # Componentes reutilizáveis
│   │   ├── Button.jsx
│   │   ├── FormError.jsx
│   │   ├── Layout.jsx
│   │   ├── PasswordInput.jsx
│   │   ├── ProtectedRoute.jsx  # ⭐ NOVO
│   │   └── TextInput.jsx
│   ├── contexts/            # ⭐ NOVO
│   │   └── AuthContext.jsx  # Contexto de autenticação
│   ├── hooks/               # ⭐ NOVO
│   │   └── useApi.js        # Hook personalizado para requisições
│   ├── pages/
│   │   ├── ForgotPassword.jsx  # ⭐ ATUALIZADO
│   │   ├── Home.jsx            # ⭐ NOVO
│   │   ├── Login.jsx           # ⭐ ATUALIZADO
│   │   ├── Register.jsx
│   │   └── ResetPassword.jsx
│   ├── services/            # ⭐ NOVO
│   │   ├── api.js           # Configuração do Axios
│   │   └── authService.js   # Serviços de autenticação
│   ├── styles/
│   ├── utils/
│   └── App.jsx              # ⭐ ATUALIZADO
├── .env                     # ⭐ NOVO - Variáveis de ambiente
└── package.json             # ⭐ ATUALIZADO
```

## ⚙️ Configuração

### 1. Instalar Dependências
```bash
cd frontend
npm install
```

### 2. Configurar Variáveis de Ambiente
Edite o arquivo `.env`:
```env
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_API_TIMEOUT=10000
```

### 3. Iniciar o Servidor de Desenvolvimento
```bash
npm start
```

## 🔑 Como Funciona a Autenticação

### 1. Login
- O usuário insere email e senha
- O frontend envia uma requisição POST para `/api/auth/login`
- O backend retorna um JWT token com dados do usuário
- O token é armazenado no localStorage
- O usuário é redirecionado para a página inicial

### 2. Gerenciamento de Token
- O Axios interceptor adiciona automaticamente o token em todas as requisições
- Token é incluído no header: `Authorization: Bearer <token>`
- Se o token expira ou é inválido (401), o usuário é deslogado automaticamente

### 3. Rotas Protegidas
- Componente `ProtectedRoute` verifica se o usuário está autenticado
- Se não estiver, redireciona para `/login`
- Se estiver, renderiza o componente solicitado

## 📡 Integração com Backend

### Endpoints Utilizados
- `POST /api/auth/login` - Login do usuário
- `POST /api/auth/forgot-password` - Recuperação de senha
- `POST /api/auth/reset-password` - Redefinição de senha
- `POST /api/auth/change-password` - Alteração de senha

### Formato das Requisições

#### Login
```json
{
  "email": "usuario@exemplo.com",
  "senha": "minhasenha"
}
```

#### Resposta do Login
```json
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "expiresAt": 1693872000000,
  "roles": ["USER", "ADMIN"],
  "nome": "João Silva"
}
```

## 🎯 Como Usar

### 1. Fazer Login
1. Acesse `http://localhost:3000/login`
2. Insira email e senha válidos
3. Clique em "Entrar"
4. Será redirecionado para a página inicial

### 2. Acessar Páginas Protegidas
- Qualquer rota protegida requer autenticação
- Se não estiver logado, será redirecionado para login

### 3. Logout
- Clique no botão "Sair" na página inicial
- Ou aguarde o token expirar automaticamente

## 🛡️ Segurança

### Medidas Implementadas
- ✅ Validação de email no frontend
- ✅ Token JWT armazenado de forma segura
- ✅ Verificação automática de expiração do token
- ✅ Limpeza automática de dados ao deslogar
- ✅ Interceptação e tratamento de erros de autenticação

### Considerações de Produção
- Em produção, considere usar httpOnly cookies em vez de localStorage
- Implemente refresh tokens para melhor segurança
- Configure HTTPS para todas as comunicações
- Implemente CORS adequadamente no backend

## 🔧 Customização

### Adicionando Novos Serviços
1. Crie um novo arquivo em `src/services/`
2. Importe e use o cliente `api` configurado
3. Exemplo:
```javascript
import api from './api';

export const leilaoService = {
  getAll: () => api.get('/leiloes'),
  getById: (id) => api.get(`/leiloes/${id}`),
  create: (data) => api.post('/leiloes', data),
};
```

### Adicionando Novas Rotas Protegidas
```jsx
<Route 
  path="/nova-rota" 
  element={
    <ProtectedRoute>
      <NovoComponente />
    </ProtectedRoute>
  } 
/>
```

## 📝 Próximos Passos

- [ ] Implementar páginas para gerenciamento de leilões
- [ ] Adicionar sistema de notificações
- [ ] Implementar upload de imagens
- [ ] Adicionar paginação nas listagens
- [ ] Implementar busca e filtros
- [ ] Adicionar testes unitários e de integração

## 🐛 Resolução de Problemas

### Erro de CORS
Se encontrar erros de CORS, certifique-se de que o backend está configurado para aceitar requisições do frontend (porta 3000).

### Token Inválido
Se o token for rejeitado pelo backend, verifique:
- Se o backend está executando
- Se as rotas da API estão corretas
- Se o formato do token está correto

### Problemas de Conexão
Verifique se o backend está executando na porta 8080 e se a URL no arquivo `.env` está correta.
