import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import TextInput from "../components/TextInput";
import PasswordInput from "../components/PasswordInput";
import Button from "../components/Button";
import FormError from "../components/FormError";
import "../styles/Register.css";
import { passwordsMatch, validateEmail, validatePassword } from "../utils/validation";

export default function Register() {
    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const valid =
        name &&
        validateEmail(email) &&
        validatePassword(password) &&
        passwordsMatch(password, confirmPassword);

    function onSubmit(e) {
        e.preventDefault();
        const errs = {};
        if (!name) errs.name = "Informe seu nome";
        if (!validateEmail(email)) errs.email = "E-mail inválido";
        if (!validatePassword(password)) errs.password = "Senha fraca";
        if (!passwordsMatch(password, confirmPassword))
            errs.confirmPassword = "As senhas não coincidem";
        setErrors(errs);
        if (Object.keys(errs).length === 0) {
            // Simular sucesso
        }
    }

    return (
        <div className="card" role="region" aria-label="Cadastro">
            <h1 className="title">Criar conta</h1>
            <form onSubmit={onSubmit} noValidate>
                <TextInput
                    id="name"
                    label="Nome"
                    placeholder="Seu nome"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    error={errors.name}
                />
                <TextInput
                    id="email"
                    label="E-mail"
                    type="email"
                    placeholder="voce@exemplo.com"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    error={errors.email}
                />
                <PasswordInput
                    id="password"
                    label="Senha"
                    placeholder="Crie uma senha"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    error={errors.password}
                />
                <PasswordInput
                    id="confirmPassword"
                    label="Confirmar senha"
                    placeholder="Repita a senha"
                    value={confirmPassword}
                    onChange={(e) => setConfirmPassword(e.target.value)}
                    error={errors.confirmPassword}
                />
                <div className="actions row">
                    <Button
                        type="button"
                        variant="secondary"
                        onClick={() => navigate("/login")}
                    >
                        Cancelar
                    </Button>
                    <Button type="submit" disabled={!valid}>
                        Cadastrar
                    </Button>
                </div>
                <FormError>{errors.form}</FormError>
            </form>
            <div className="links">
                Já possui conta? <Link to="/login">Entrar</Link>
            </div>
        </div>
    );
}
