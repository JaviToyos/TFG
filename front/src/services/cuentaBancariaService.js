import api from "./gestorCuentasAPI";

class CuentaBancariaService {
  buscarTodos() {
    return api.get("/cuentaBancaria");
  }

  buscarPorId(id) {
    return api.get(`/cuentaBancaria/${id}`);
  }

  crear(data) {
    return api.post("/cuentaBancaria", data);
  }

  modificar(id, data) {
    return api.put(`/cuentaBancaria/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/cuentaBancaria/${id}`);
  }

}

export default new CuentaBancariaService();
