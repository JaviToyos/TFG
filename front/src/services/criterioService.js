import api from "./gestorCuentasAPI";

class CriterioService {
  buscarTodos() {
    return api.get("/criterio");
  }

  buscarPorId(id) {
    return api.get(`/criterio/${id}`);
  }

  crear(data) {
    return api.post("/criterio", data);
  }

  modificar(id, data) {
    return api.put(`/criterio/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/criterio/${id}`);
  }

}

export default new CriterioService();
