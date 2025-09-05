import { useAuth } from '../contexts/AuthContext';
import Button from '../components/Button';

export default function Home() {
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
  };

  return (
    <div className="card">
      <h1>Bem-vindo ao Sistema de Leilão</h1>
      <p>Olá, {user?.nome}!</p>
      <p>Você está logado com sucesso.</p>
      
      {user?.roles && (
        <div>
          <h3>Seus perfis:</h3>
          <ul>
            {user.roles.map((role, index) => (
              <li key={index}>{role}</li>
            ))}
          </ul>
        </div>
      )}
      
      <div className="actions">
        <Button onClick={handleLogout}>Sair</Button>
      </div>
    </div>
  );
}
