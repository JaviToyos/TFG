import api from "./gestorCuentasAPI";

class CategoriaMovimientoService {
  buscarTodos() {
    return api.get("/categoriaMovimiento");
  }

  buscarPorId(id) {
    return api.get(`/categoriaMovimiento/${id}`);
  }

  crear(data) {
    return api.post("/categoriaMovimiento", data);
  }

  modificar(id, data) {
    return api.put(`/categoriaMovimiento/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/categoriaMovimiento/${id}`);
  }

}

export default new CategoriaMovimientoService();
