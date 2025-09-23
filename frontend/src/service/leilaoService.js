import api from "../config/axiosConfig";

class LeilaoService {
    constructor() {
        this.api = api;
    }

    async getAll(page = 0, size = 10) {
        const response = await api.get(`/api/leiloes?page=${page}&size=${size}`);
        return response;
    }

    async getById(id) {
        const response = await api.get(`/api/leiloes/${id}`);
        return response;
    }

    async create(data) {
        const response = await api.post("/api/leiloes", data);
        return response;
    }

    async update(id, data) {
        const response = await api.put(`/api/leiloes/${id}`, data);
        return response;
    }

    async delete(id) {
        await api.delete(`/api/leiloes/${id}`);
    }
}

export default LeilaoService;
