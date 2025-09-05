import '../styles/Button.css';

export default function Button({ children, onClick, type = 'button', variant = 'primary', disabled }) {
  return (
    <button
      type={type}
      className={`btn ${variant}`}
      onClick={onClick}
      disabled={disabled}
    >
      {children}
    </button>
  );
}
