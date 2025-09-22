import React, { useState } from "react";
import PessoaService from "../../service/pessoaService";

const Cadastro = (props) => {
    const [form, setForm] = useState({
        nome: "",
        email: "",
        senha: "",
    });
    const service = new PessoaService();

    const [mensagem, setMensagem] = useState("");

    const handleChange = (e) => {
        setForm({
            ...form,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await service.inserir(form);
            setMensagem("Usuário cadastrado com sucesso");
            setForm({ nome: "", email: "", senha: "" });
        } catch (error) {
            setMensagem("Erro ao cadastrar usuário.");
        }
    };

    return (
        <div>
            <h2>Cadastro de usuário</h2>
            <form onSubmit={handleSubmit}>
                <input
                    name="nome"
                    placeholder="Nome"
                    value={form.nome}
                    onChange={handleChange}
                    className="input"
                    required
                />
                <input
                    name="email"
                    type="email"
                    placeholder="E-mail"
                    value={form.email}
                    onChange={handleChange}
                    className="input"
                    required
                />
                <input
                    name="senha"
                    type="password"
                    placeholder="Senha"
                    value={form.senha}
                    onChange={handleChange}
                    className="input"
                    required
                />
                <button type="submit">Cadastrar</button>
                {mensagem && <div>{mensagem}</div>}
            </form>
        </div>
    );
};

export default Cadastro;
