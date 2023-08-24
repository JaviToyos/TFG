import api from "./gestorCuentasAPIAuthorization";

class CuentaBancariaService {
    buscarCuentas(username) {
        return api.get(`/cuentaBancaria/findByUsername?username=${username}`);
    }

    guardar(item, usuario) {
        const data = {
            accountID: item.value.id,
            usuario: {
                username: usuario
            },
            proveedor: {
                name: 'GoCardless'
            },
            nombre: item.label,
            accountDetails: {
                iban: item.value.iban,
                currency: item.value.currency
            },
            accountBalance: {
                balanceAmount: {
                    amount: item.value.amount
                }
            }
        };
        return api.post("/cuentaBancaria/save", data);
    }

    eliminar(id) {
        const cuentaBancaria = {
            accountID: id
        }
        return api.post(`/cuentaBancaria/deleteCuenta`, cuentaBancaria);
    }

}

export default new CuentaBancariaService();
