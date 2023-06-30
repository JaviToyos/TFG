import api from "./gestorCuentasAPI";

class PersonaService {
  buscarTodos() {
    return api.get("/persona");
  }

  buscarPorId(id) {
    return api.get(`/persona/${id}`);
  }

  crear(data) {
    return api.post("/persona", data);
  }

  modificar(id, data) {
    return api.put(`/persona/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/persona/${id}`);
  }

}

export default new PersonaService();
