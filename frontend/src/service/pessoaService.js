import api from "../config/axiosConfig";

class PessoaService {
    constructor() {
        this.api = api;
    }

    async inserir(dados) {
      const response = await this.api.post("/api/pessoas", dados);
      return response;
    }
}

export default PessoaService;
