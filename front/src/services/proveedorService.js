import api from "./gestorCuentasAPI";

class ProveedorService {
  buscarTodos() {
    return api.get("/proveedor");
  }

  buscarPorId(id) {
    return api.get(`/proveedor/${id}`);
  }

  crear(data) {
    return api.post("/proveedor", data);
  }

  modificar(id, data) {
    return api.put(`/proveedor/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/proveedor/${id}`);
  }

}

export default new ProveedorService();
