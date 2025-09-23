import React, { useState, useEffect } from "react";
import LeilaoService from "../../service/leilaoService";
import { Link } from "react-router-dom";
import Header from "../../components/Header/Header";

const Leiloes = () => {
    const [leiloes, setLeiloes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const leilaoService = new LeilaoService();

    useEffect(() => {
        const fetchLeiloes = async () => {
            try {
                setLoading(true);
                const response = await leilaoService.getAll();
                setLeiloes(response.data.content);
            } catch (err) {
                setError("Falha ao carregar leilões");
                console.error(err);
            } finally {
                setLoading(false);
            }
        };

        fetchLeiloes();
    }, []);

    if (loading) return <div>Carregando...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <Header pageTitle="Leilões" />
            <Link to={"/leiloes/novo"}>
                <button>Novo Leilão</button>
            </Link>
            <table>
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Status</th>
                        <th>Ações</th>
                    </tr>
                </thead>
                <tbody>
                    {leiloes.length === 0 ? (
                        <tr>
                            <td colSpan="3">Nenhum leilão encontrado.</td>
                        </tr>
                    ) : (
                        leiloes.map((leilao) => (
                            <tr key={leilao.id}>
                                <td>{leilao.nome}</td>
                                <td>{leilao.status}</td>
                                <td>
                                    <button>Editar</button>
                                    <button>Deletar</button>
                                </td>
                            </tr>
                        ))
                    )}
                </tbody>
            </table>
        </div>
    );
};

export default Leiloes;
