import '../styles/TextInput.css';

export default function TextInput({ id, label, type = 'text', placeholder, value, onChange, error }) {
  return (
    <div className="field">
      {label && (
        <label className="label" htmlFor={id}>{label}</label>
      )}
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
      {error && (
        <div id={`${id}-error`} className="form-error">{error}</div>
      )}
    </div>
  );
}
