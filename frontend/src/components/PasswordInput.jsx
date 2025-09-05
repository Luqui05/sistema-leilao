import { useState } from 'react';
import '../styles/PasswordInput.css';

export default function PasswordInput({ id, label, placeholder, value, onChange, error }) {
  const [show, setShow] = useState(false);
  const type = show ? 'text' : 'password';
  return (
    <div className="field">
      {label && <label className="label" htmlFor={id}>{label}</label>}
      <div className="password-row">
        <input
          id={id}
          className={`input ${error ? 'input-error' : ''}`}
          type={type}
          placeholder={placeholder}
          value={value}
          onChange={onChange}
          aria-invalid={!!error}
          aria-describedby={error ? `${id}-error` : undefined}
        />
        <button
          type="button"
          className="show-btn"
          aria-label={show ? 'Ocultar senha' : 'Mostrar senha'}
          onClick={() => setShow(s => !s)}
        >
          {show ? 'Ocultar' : 'Mostrar'}
        </button>
      </div>
      {error && <div id={`${id}-error`} className="form-error">{error}</div>}
    </div>
  );
}
