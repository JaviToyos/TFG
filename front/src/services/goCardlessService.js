import api from "./gestorCuentasAPIAuthorization";


class GoCardlessService {
    obtenerToken() {
        return api.post('/goCardless/access');
    }

    obtenerBancos(tokenGoCardless) {
        const token = {
            token: tokenGoCardless
        }
        return api.post('/goCardless/availableBanks', token);
    }

    obtenerLinkBanco(tokenGoCardless, idBank) {
        const request = {
            token: tokenGoCardless,
            bank: {
                id: idBank
            }
        }
        return api.post('/goCardless/bank', request);

    }

    obtenerCuentas(tokenGoCardless, bankLink) {
        const request = {
            token: tokenGoCardless,
            bankLink: {
                id: bankLink
            }
        }

        return api.post('/goCardless/accountList', request);
    }

    obtenerDetalleCuentas(tokenGoCardless, idCuenta) {
        const request = {
            token: tokenGoCardless,
            accountId: idCuenta
        }
        return api.post('/goCardless/accountDetails', request);

    }

    obtenerBalanceCuenta(tokenGoCardless, idCuenta) {
        const request = {
            token: tokenGoCardless,
            accountId: idCuenta
        }
        return api.post('/goCardless/accountDetails', request);

    }

    obtenerMovimientos(tokenGoCardless, idCuenta) {
        const request = {
            token: tokenGoCardless,
            accountId: idCuenta
        }
        return api.post('/goCardless/accountTransactions', request);

    }

}

export default new GoCardlessService();
