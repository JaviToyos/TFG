import React, {useState, useEffect} from 'react';
import {InputText} from 'primereact/inputtext';
import {InputNumber} from 'primereact/inputnumber';
import {Dropdown} from 'primereact/dropdown';
import {Button} from 'primereact/button';
import {Divider} from 'primereact/divider';
import {classNames} from 'primereact/utils';
import {useParams, useNavigate} from "react-router-dom";

import LoginService from "../../services/loginService";
import {ProgressSpinner} from "primereact/progressspinner";

export default function BackUpOk() {
    const params = useParams();
    const navigate = useNavigate();

    const recuperarPassItemVacio = {username: "", email: ""};
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [recuperarPassItem, setRecuperarPassItem] = useState(recuperarPassItemVacio);
    const [confirmacion, setConfirmacion] = useState(false);

    useEffect(() => {
        // if (!esNuevo) {
        //     loginService.buscarPorId(params.idArticulo).then(res => setUsuario(res.data));
        // }
        // familiaService.buscarTodas().then(res => setFamilias(res.data));
    }, []);

    function login() {
        navigate("../../../login");
    }

    return (
        <div>
            <div className="recuadro"
                 style={{border: '1px solid black', padding: '10px', margin: 'auto', backgroundColor: 'lightgray'}}>
                <p>El correo de recuperación se ha enviado correctamente.</p>
                <Button label="Aceptar" icon="pi pi-plus" className="p-button-lg" onClick={login}
                        tooltip="Volver al login" tooltipOptions={{position: 'bottom'}}/>
            </div>
        </div>
    );
}