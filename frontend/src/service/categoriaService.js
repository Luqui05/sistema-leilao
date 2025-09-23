import api from "../config/axiosConfig";

class CategoriaService {
    constructor() {
        this.api = api;
    }

    async getAll(page = 0, size = 10) {
        const response = await this.api.get(
            `/api/categorias?page=${page}&size=${size}`
        );
        return response;
    }

    async create(data) {
        const response = await this.api.post("/api/categorias", data);
        return response;
    }

    async getById(id) {
        const response = await api.get(`/api/categorias/${id}`);
        return response;
    }

    async update(id, data) {
        const response = api.put(`/api/categorias/${id}`, data);
        return response;
    }

    async delete(id) {
        await api.delete(`/api/categorias/${id}`);
    }
}

export default CategoriaService;
