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

export default function PassBackUp() {
    const params = useParams();
    const navigate = useNavigate();

    const recuperarPassItemVacio = { username: "", email: "" };
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [recuperarPassItem, setRecuperarPassItem] = useState(recuperarPassItemVacio);

    useEffect(() => {
        // if (!esNuevo) {
        //     loginService.buscarPorId(params.idArticulo).then(res => setUsuario(res.data));
        // }
        // familiaService.buscarTodas().then(res => setFamilias(res.data));
    }, []);

    function recuperarPass() {
        recuperarPassItemVacio.username = username;
        recuperarPassItemVacio.email = email;
        setRecuperarPassItem(recuperarPassItemVacio);
        LoginService.recuperarPass(recuperarPassItem).then(res => {
            confirmOk();
        }).catch(res => {
            confirmFailure();
        });
    }

    function onUsernameChange(e) {
        setUsername(e.target.value);
    }

    function onEmailChange(e) {
        setEmail(e.target.value);
    }

    function login() {
        navigate("../../login");
    }

    function confirmOk() {
        navigate("backUpOk");
    }

    function confirmFailure() {
        navigate("backUpFail");
    }

    return (
        <div>
            <div className="form login" style={{border: '1px solid black', padding: '10px', margin: '10%'}}>
                <div className="text-3xl text-800 font-bold mb-4"> Recuperar contraseña</div>

                <div className="grid">
                    <InputText id="username" className="col-6 mr-2" onChange={onUsernameChange}/>
                    <InputText id="email" className="col-6 mr-2" onChange={onEmailChange}/>
                    <Button label="recuperarPass" className="col-1 mr-2" onClick={recuperarPass}/>
                </div>

                <div className="flex justify-content-end">
                    <Button label="login" icon="pi pi-plus" className="p-button-lg" onClick={login}
                            tooltip="Volver al login" tooltipOptions={{position: 'bottom'}}/>
                </div>
            </div>

        </div>
    );
}