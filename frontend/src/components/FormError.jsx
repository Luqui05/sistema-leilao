import '../styles/FormError.css';

export default function FormError({ children, id }) {
  if (!children) return null;
  return (
    <div id={id} className="form-error" role="alert">
      <span className="underline">{children}</span>
    </div>
  );
}
