import React, { useState } from "react";
import Header from "../../components/Header/Header";
import { Link } from "react-router-dom";

const Login = () => {
    const [form, setForm] = useState({
        email: "",
        senha: "",
    });
    const [mensagem, setMensagem] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        // TODO: chamada para autenticação no backend
        setMensagem("Autenticação não implementada.");
    };

    return (
        <div>
            <Header pageTitle="Login" />
            <form onSubmit={handleSubmit}>
                <input
                    name="email"
                    type="email"
                    placeholder="E-mail"
                    value={form.email}
                    onChange={handleChange}
                    required
                />
                <input
                    name="senha"
                    type="password"
                    placeholder="Senha"
                    value={form.senha}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Acessar</button>
                <Link to="/register">
                    <button type="button">Cadastrar-se</button>
                </Link>
                <Link to="/recuperar-senha">
                    <button type="button">Recuperar Senha</button>
                </Link>
                {mensagem && <div>{mensagem}</div>}
            </form>
        </div>
    );
};

export default Login;
