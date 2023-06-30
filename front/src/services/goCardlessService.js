import api from "./gestorCuentasAPI";

class GoCardlessService {
  buscarTodos() {
    return api.get("/goCardless");
  }

  buscarPorId(id) {
    return api.get(`/goCardless/${id}`);
  }

  crear(data) {
    return api.post("/goCardless", data);
  }

  modificar(id, data) {
    return api.put(`/goCardless/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/goCardless/${id}`);
  }

}

export default new GoCardlessService();
