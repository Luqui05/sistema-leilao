import React, { useState, useMemo } from "react";
import Header from "../../components/Header/Header";
import AuthService from "../../service/authService";

const passwordChecks = (pwd) => {
    return {
        length: pwd.length >= 6,
        upper: /[A-Z]/.test(pwd),
        lower: /[a-z]/.test(pwd),
        number: /[0-9]/.test(pwd),
        special: /[^A-Za-z0-9]/.test(pwd),
    };
};

const AlterarSenha = () => {
    const [form, setForm] = useState({
        email: "",
        codigo: "",
        senha: "",
        confirmar: "",
    });
    const [mensagem, setMensagem] = useState("");
    const [loading, setLoading] = useState(false);
    const authService = new AuthService();

    const checks = useMemo(() => passwordChecks(form.senha), [form.senha]);
    const allValid =
        checks.length &&
        checks.upper &&
        checks.lower &&
        checks.number &&
        checks.special &&
        form.senha === form.confirmar &&
        form.codigo.trim() !== "" &&
        form.email.trim() !== "";

    const handleChange = (e) => {
        setForm({ ...form, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!allValid) {
            setMensagem("Preencha corretamente os campos e atenda os requisitos de senha.");
            return;
        }
        setLoading(true);
        setMensagem("");
        try {
            await authService.resetPassword(form.email, form.codigo, form.senha);
            setMensagem("Senha alterada com sucesso.");
            setForm({ email: "", codigo: "", senha: "", confirmar: "" });
        } catch (err) {
            setMensagem(err?.response?.data?.message || "Erro ao alterar senha.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <Header pageTitle="Alterar Senha" />
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
                    name="codigo"
                    placeholder="Código de verificação"
                    value={form.codigo}
                    onChange={handleChange}
                    required
                />
                <input
                    name="senha"
                    type="password"
                    placeholder="Nova senha"
                    value={form.senha}
                    onChange={handleChange}
                    required
                />
                <input
                    name="confirmar"
                    type="password"
                    placeholder="Confirmar nova senha"
                    value={form.confirmar}
                    onChange={handleChange}
                    required
                />

                <div>
                    <p>Requisitos da senha:</p>
                    <ul>
                        <li style={{ color: checks.length ? "green" : "red" }}>
                            Mínimo 6 caracteres
                        </li>
                        <li style={{ color: checks.upper ? "green" : "red" }}>
                            Uma letra maiúscula
                        </li>
                        <li style={{ color: checks.lower ? "green" : "red" }}>
                            Uma letra minúscula
                        </li>
                        <li style={{ color: checks.number ? "green" : "red" }}>
                            Um número
                        </li>
                        <li style={{ color: checks.special ? "green" : "red" }}>
                            Um caractere especial
                        </li>
                        <li style={{ color: form.senha === form.confirmar ? "green" : "red" }}>
                            Senhas coincidem
                        </li>
                    </ul>
                </div>

                <button type="submit" disabled={!allValid || loading}>
                    {loading ? "Alterando..." : "Alterar Senha"}
                </button>

                {mensagem && <div>{mensagem}</div>}
            </form>
        </div>
    );
};

export default AlterarSenha;
