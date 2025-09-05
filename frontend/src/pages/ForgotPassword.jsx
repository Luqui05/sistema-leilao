import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TextInput from '../components/TextInput';
import Button from '../components/Button';
import FormError from '../components/FormError';
import '../styles/ForgotPassword.css';
import { validateEmail } from '../utils/validation';
import { authService } from '../services/authService';

export default function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState(false);
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const valid = validateEmail(email);

  async function onSubmit(e) {
    e.preventDefault();
    if (!valid) { 
      setError('E-mail inválido'); 
      return; 
    }
    
    setLoading(true);
    setError('');
    
    try {
      await authService.forgotPassword(email);
      setSuccess(true);
    } catch (error) {
      console.error('Erro ao enviar código de recuperação:', error);
      if (error.response?.status === 404) {
        setError('E-mail não encontrado.');
      } else {
        setError('Erro ao enviar código. Tente novamente.');
      }
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="card" role="region" aria-label="Recuperar senha">
      <h1 className="title">Recuperar senha</h1>
      {success ? (
        <div>
          <p>Código de recuperação enviado para {email}</p>
          <div className="actions">
            <Button onClick={() => navigate('/reset-password')}>
              Redefinir senha
            </Button>
          </div>
        </div>
      ) : (
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
          <FormError>{error}</FormError>
          <div className="actions row">
            <Button 
              type="button" 
              variant="secondary" 
              onClick={() => navigate('/login')}
            >
              Cancelar
            </Button>
            <Button 
              type="submit" 
              disabled={!valid || loading}
            >
              {loading ? 'Enviando...' : 'Enviar código de recuperação'}
            </Button>
          </div>
        </form>
      )}
    </div>
  );
}
