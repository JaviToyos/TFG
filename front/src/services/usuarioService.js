import api from "./gestorCuentasAPI";

class UsuarioService {
  buscarTodos() {
    return api.get("/usuario");
  }

  buscarPorId(id) {
    return api.get(`/usuario/${id}`);
  }

  crear(data) {
    return api.post("/usuario", data);
  }

  modificar(id, data) {
    return api.put(`/usuario/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/usuario/${id}`);
  }

}

export default new UsuarioService();
