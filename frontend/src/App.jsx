import { BrowserRouter, Route, Routes } from "react-router-dom";
import Cadastro from "./pages/Cadastro/Cadastro";

const App = () => {
    return (
        <BrowserRouter>
          <Routes>
            <Route path="/register" element={<Cadastro />} />
          </Routes>
        </BrowserRouter>
    );
};

export default App;
