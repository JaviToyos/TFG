import api from "./gestorCuentasAPI";

class LoginService {
    iniciarSesion(login) {
        return api.post("/login", login);
    }

    recuperarPass(recuperarPass) {
        return api.post(`/login/passBackUp`, recuperarPass);
    }
}

export default new LoginService();
