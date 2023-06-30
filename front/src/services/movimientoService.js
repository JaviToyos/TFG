import api from "./gestorCuentasAPI";

class MovimientoService {
  buscarTodos() {
    return api.get("/movimiento");
  }

  buscarPorId(id) {
    return api.get(`/movimiento/${id}`);
  }

  crear(data) {
    return api.post("/movimiento", data);
  }

  modificar(id, data) {
    return api.put(`/movimiento/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/movimiento/${id}`);
  }

}

export default new MovimientoService();
