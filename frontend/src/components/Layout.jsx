import { Link } from 'react-router-dom';
import '../styles/Layout.css';

export default function Layout({ children }) {
  return (
    <div className="app-root">
      <header className="app-header" role="banner">
        <div className="brand" aria-label="Site name">SL</div>
        <nav aria-label="Main navigation" className="nav">
          <Link to="/login">Login</Link>
          <Link to="/register">Cadastrar</Link>
        </nav>
      </header>
      <main className="app-main" role="main">{children}</main>
    </div>
  );
}
