import api from "./gestorCuentasAPIAuthorization";

class CategoriaService {
    buscarPorUsuario(username) {
        return api.get(`/categoria/findByUsername?username=${username}`);
    }

    crear(categoria) {
        return api.post("/categoria/addCategoria", categoria);
    }

    modificar(data) {
        return api.post(`/categoria/modificar`, data);
    }

    eliminar(id) {
        return api.post(`/categoria/eliminar`, id);
    }

}

export default new CategoriaService();
