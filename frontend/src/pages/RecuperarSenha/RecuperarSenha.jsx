import React, { useState } from "react";
import Header from "../../components/Header/Header";
import AuthService from "../../service/authService";

const RecuperarSenha = () => {
    const [email, setEmail] = useState("");
    const [mensagem, setMensagem] = useState("");
    const [loading, setLoading] = useState(false);
    const authService = new AuthService();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setMensagem("");
        try {
            await authService.forgotPassword(email);
            setMensagem("Código de verificação enviado para o e-mail.");
            setEmail("");
        } catch (err) {
            setMensagem(err?.response?.data?.message || "Erro ao enviar código.");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div>
            <Header pageTitle="Recuperar Senha" />
            <form onSubmit={handleSubmit}>
                <input
                    name="email"
                    type="email"
                    placeholder="E-mail"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <button type="submit" disabled={loading}>
                    {loading ? "Enviando..." : "Enviar código"}
                </button>
                {mensagem && <div>{mensagem}</div>}
            </form>
        </div>
    );
};

export default RecuperarSenha;
