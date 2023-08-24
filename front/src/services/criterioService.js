import api from "./gestorCuentasAPIAuthorization";

class CriterioService {

    buscarPorCategoria(idCategoria) {
        return api.get(`/criterio/findByCategoria?categoria=${idCategoria}`);
    }

    crear(criterio) {
        return api.post("/criterio/addCriterio", criterio);
    }

    modificar(data) {
        return api.post(`/criterio/modifyCriterio`, data);
    }

    eliminar(id) {
        return api.post(`/criterio/eliminar`, id);
    }

}

export default new CriterioService();
