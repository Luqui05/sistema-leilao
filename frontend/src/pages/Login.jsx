import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import TextInput from '../components/TextInput';
import PasswordInput from '../components/PasswordInput';
import Button from '../components/Button';
import FormError from '../components/FormError';
import '../styles/Login.css';
import { validateEmail } from '../utils/validation';

export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const valid = validateEmail(email) && password.length > 0;

  function onSubmit(e) {
    e.preventDefault();
    if (!valid) {
      setError('Preencha e-mail válido e senha.');
      return;
    }
    setError('');
    // Fake login; stay on page
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
          <Button type="submit" disabled={!valid}>Entrar</Button>
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
