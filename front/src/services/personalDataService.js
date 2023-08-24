import api from "./gestorCuentasAPIAuthorization";

class PersonalDataService {

    updateDatos(datosPersonales) {
        return api.post(`/datosPersonales/update`, datosPersonales);
    }

    buscarDatos(username) {
        return api.get(`/datosPersonales/findByUsername?username=${username}`);
    }
}

export default new PersonalDataService();
