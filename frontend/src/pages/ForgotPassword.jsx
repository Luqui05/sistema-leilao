import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import TextInput from '../components/TextInput';
import Button from '../components/Button';
import FormError from '../components/FormError';
import '../styles/ForgotPassword.css';
import { validateEmail } from '../utils/validation';

export default function ForgotPassword() {
  const [email, setEmail] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const valid = validateEmail(email);

  function onSubmit(e) {
    e.preventDefault();
    if (!valid) { setError('E-mail inválido'); return; }
    setError('');
  }

  return (
    <div className="card" role="region" aria-label="Recuperar senha">
      <h1 className="title">Recuperar senha</h1>
      <form onSubmit={onSubmit} noValidate>
        <TextInput id="email" label="E-mail" type="email" placeholder="voce@exemplo.com" value={email} onChange={(e)=>setEmail(e.target.value)} error={error} />
        <div className="actions row">
          <Button type="button" variant="secondary" onClick={()=>navigate('/login')}>Cancelar</Button>
          <Button type="submit" disabled={!valid}>Enviar código de recuperação</Button>
        </div>
      </form>
    </div>
  );
}
