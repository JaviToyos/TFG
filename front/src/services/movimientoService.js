import api from "./gestorCuentasAPIAuthorization";

class MovimientoService {
    buscarPorIdCuenta(idCuenta) {
        const data = {
            idCuenta: idCuenta
        };
        return api.post('/movimientos/findByCuenta', data);
    }
}

export default new MovimientoService();
