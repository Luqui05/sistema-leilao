import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import TextInput from '../components/TextInput';
import PasswordInput from '../components/PasswordInput';
import Button from '../components/Button';
import FormError from '../components/FormError';
import '../styles/Login.css';
import { validateEmail } from '../utils/validation';
import { useAuth } from '../contexts/AuthContext';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();
  const { login } = useAuth();

  const valid = validateEmail(email) && password.length > 0;

  async function onSubmit(e) {
    e.preventDefault();
    if (!valid) {
      setError('Preencha e-mail válido e senha.');
      return;
    }

    setLoading(true);
    setError('');

    try {
      await login({ email, password });
      // Redirecionar para a página inicial ou dashboard após login bem-sucedido
      navigate('/');
    } catch (error) {
      console.error('Erro no login:', error);
      
      // Tratar diferentes tipos de erro
      if (error.response?.status === 401) {
        setError('E-mail ou senha incorretos.');
      } else if (error.response?.status === 400) {
        setError('Dados inválidos. Verifique os campos.');
      } else if (error.code === 'ECONNREFUSED' || error.code === 'ERR_NETWORK') {
        setError('Erro de conexão. Verifique se o servidor está funcionando.');
      } else {
        setError('Erro interno do servidor. Tente novamente.');
      }
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="card" role="region" aria-label="Login">
      <h1 className="title">Bem-vindo de volta</h1>
      <form onSubmit={onSubmit} noValidate>
        <TextInput
          id="email"
          label="E-mail"
          type="email"
          placeholder="voce@exemplo.com"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          error={email && !validateEmail(email) ? 'E-mail inválido' : ''}
        />
        <PasswordInput
          id="password"
          label="Senha"
          placeholder="Sua senha"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <FormError>{error}</FormError>
        <div className="actions">
          <Button type="submit" disabled={!valid || loading}>
            {loading ? 'Entrando...' : 'Entrar'}
          </Button>
        </div>
      </form>
      <div className="links">
        <Link to="/forgot-password">Recuperar senha</Link>
      </div>
      <div className="links">
        Não tem conta? <Link to="/register">Cadastrar-se</Link>
      </div>
    </div>
  );
}
