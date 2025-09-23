import api from "../config/axiosConfig";

class AuthService {
    constructor() {
        this.api = api;
    }

    async forgotPassword(email) {
        // envia { email } para POST /api/auth/forgot-password
        const response = await this.api.post("/api/auth/forgot-password", { email });
        return response;
    }

    async resetPassword(email, codigo, novaSenha) {
        // envia { email, codigo, novaSenha } para POST /api/auth/reset-password
        const response = await this.api.post("/api/auth/reset-password", {
            email,
            codigo,
            novaSenha,
        });
        return response;
    }
}

export default AuthService;