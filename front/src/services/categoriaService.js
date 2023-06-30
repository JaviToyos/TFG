import api from "./gestorCuentasAPI";

class CategoriaService {
  buscarTodos() {
    return api.get("/categoria");
  }

  buscarPorId(id) {
    return api.get(`/categoria/${id}`);
  }

  crear(data) {
    return api.post("/categoria", data);
  }

  modificar(id, data) {
    return api.put(`/categoria/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/categoria/${id}`);
  }

}

export default new CategoriaService();
