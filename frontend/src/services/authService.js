import api from './api';

export const authService = {
  // Login do usuário
  login: async (credentials) => {
    try {
      const response = await api.post('/auth/login', {
        email: credentials.email,
        senha: credentials.password
      });
      
      const { token, expiresAt, roles, nome } = response.data;
      
      // Armazenar token e dados do usuário no localStorage
      localStorage.setItem('authToken', token);
      localStorage.setItem('user', JSON.stringify({
        nome,
        roles,
        expiresAt
      }));
      
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  // Logout do usuário
  logout: () => {
    localStorage.removeItem('authToken');
    localStorage.removeItem('user');
  },

  // Verificar se o usuário está autenticado
  isAuthenticated: () => {
    const token = localStorage.getItem('authToken');
    const user = localStorage.getItem('user');
    
    if (!token || !user) {
      return false;
    }
    
    try {
      const userData = JSON.parse(user);
      const now = Date.now();
      
      // Verificar se o token não expirou
      if (userData.expiresAt && now > userData.expiresAt) {
        authService.logout();
        return false;
      }
      
      return true;
    } catch (error) {
      authService.logout();
      return false;
    }
  },

  // Obter dados do usuário
  getUser: () => {
    const user = localStorage.getItem('user');
    if (user) {
      try {
        return JSON.parse(user);
      } catch (error) {
        return null;
      }
    }
    return null;
  },

  // Recuperar senha
  forgotPassword: async (email) => {
    try {
      await api.post('/auth/forgot-password', { email });
    } catch (error) {
      throw error;
    }
  },

  // Redefinir senha
  resetPassword: async (email, codigo, novaSenha) => {
    try {
      await api.post('/auth/reset-password', {
        email,
        codigo,
        novaSenha
      });
    } catch (error) {
      throw error;
    }
  },

  // Alterar senha
  changePassword: async (senhaAtual, novaSenha) => {
    try {
      await api.post('/auth/change-password', {
        senhaAtual,
        novaSenha
      });
    } catch (error) {
      throw error;
    }
  }
};
