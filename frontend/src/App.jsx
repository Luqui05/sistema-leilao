import { BrowserRouter, Route, Routes } from "react-router-dom";
import Cadastro from "./pages/Cadastro/Cadastro";
import Leiloes from "./pages/Leiloes/Leiloes";
import AuthProvider from "./components/AuthProvider/AuthProvider";
import ProtectedRoute from "./components/ProtectedRoute/ProtectedRoute";
import Login from "./pages/Login/Login";
import RecuperarSenha from "./pages/RecuperarSenha/RecuperarSenha";
import AlterarSenha from "./pages/AlterarSenha/AlterarSenha";

const App = () => {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    {/* Rotas públicas */}
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Cadastro />} />
                    <Route
                        path="/recuperar-senha"
                        element={<RecuperarSenha />}
                    />
                    <Route
                        path="/alterar-senha"
                        element={<AlterarSenha />}
                    />

                    {/* Rotas protegidas (precisa autenticar) */}
                    {/* <Route path="/" element={<ProtectedRoute><Home /></ProtectedRoute>} /> */}
                    <Route
                        path="/leiloes"
                        element={
                            <ProtectedRoute>
                                <Leiloes />
                            </ProtectedRoute>
                        }
                    />
                    {/* <Route path="/leiloes/novo" element={<ProtectedRoute><LeilaoForm /></ProtectedRoute>} /> */}
                    {/* <Route path="/leiloes/editar/:id" element={<ProtectedRoute><LeilaoForm /></ProtectedRoute>} /> */}

                    {/* TODO: DESENVOLVER OUTRAS ROTAS (DO OUTRO CRUD) */}
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
};

export default App;
