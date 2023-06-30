import React, { useState, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';
import { useParams, useNavigate } from "react-router-dom";

import LoginService from "../../services/loginService";
import {ProgressSpinner} from "primereact/progressspinner";

export default function Login() {
    const navigate = useNavigate();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [cargando, setCargando] = useState(false);

    useEffect(() => {
        // if (!esNuevo) {
        //     loginService.buscarPorId(params.idArticulo).then(res => setUsuario(res.data));
        // }
        // familiaService.buscarTodas().then(res => setFamilias(res.data));
    },[]);

    function iniciarSesion(){
        const loginItem = {username: username, passwd: password};
        LoginService.iniciarSesion(loginItem).then(res => {
            home();
        });
    }

    function onUsernameChange(e){
        setUsername(e.target.value);
    }
    function onPasswordChange(e){
        setPassword(e.target.value);
    }

    function recuperarPass() {
        navigate("recuperarPass"); // navega a URL para creacion de nuevo cliente
    }

    function home() {
        navigate("../home"); // navega a URL para creacion de nuevo cliente
    }

    return (
        <div>
            {/* Contenido de la página */}
            <div className="form login" style= {{border:'1px solid black', padding:'10px', margin:'10%'}}>
                <div className="text-3xl text-800 font-bold mb-4"> Iniciar Sesión </div>

                <div className="grid">
                    <InputText id="username" className="col-6 mr-2" onChange={onUsernameChange} />
                    <InputText id="password" className="col-6 mr-2" onChange={onPasswordChange} />
                    <Button label="login" className="col-1 mr-2" onClick={iniciarSesion} />
                </div>

                <div className="flex justify-content-end">
                    <Button label="recuperarPass" icon="pi pi-plus" className="p-button-lg" onClick={recuperarPass} tooltip="Recuperar Contraseña" tooltipOptions={{ position: 'bottom' }} />
                </div>
            </div>

        </div>
    );
}