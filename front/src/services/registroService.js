import api from "./gestorCuentasAPI";

class RegistroService {
    registrar(data) {
        return api.post("/registro", data);
    }
}

export default new RegistroService();
