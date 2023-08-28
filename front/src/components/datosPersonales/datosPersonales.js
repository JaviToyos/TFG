import React, {useState, useEffect} from 'react';
import {useNavigate} from "react-router-dom";
import {Cookies} from "react-cookie";

import {Button} from 'primereact/button';
import {Divider} from 'primereact/divider';

import PersonalDataService from "../../services/personalDataService";
import "./DatosPersonales.css";
import {InputText} from "primereact/inputtext";
import md5 from "md5";
import {Dialog} from "primereact/dialog";

export default function DatosPersonales() {
    const navigate = useNavigate();

    const datosVacios = {
        persona: {dni: "", nombre: "", apellidos: "", email: ""},
        usuario: {username: "", password: ""}
    };
    const [datos, setDatos] = useState(datosVacios);
    const [password, setPassword] = useState("");
    const [nombre, setNombre] = useState("");
    const [apellidos, setApellidos] = useState("");
    const [email, setEmail] = useState("");
    const [message, setMessage] = useState('');
    const [error, setError] = useState('');
    const [editing, setEditing] = useState(false);
    const [showSuccessDialog, setShowSuccessDialog] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
        PersonalDataService.buscarDatos(getCookie("usuario"))
            .then(res => setDatos(res.data));
    }, [editing]);

    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    function updateDatosPersonales() {
        const datosPersonales = {
            persona: {dni: datos.persona.dni, nombre: nombre, apellidos: apellidos, email: email},
            usuario: {username: datos.usuario.username, password: password}
        };

        if (datosPersonales.persona.nombre === "") datosPersonales.persona.nombre = datos.persona.nombre;
        if (datosPersonales.persona.apellidos === "") datosPersonales.persona.apellidos = datos.persona.apellidos;
        if (datosPersonales.persona.email === "") datosPersonales.persona.email = datos.persona.email;
        if (datosPersonales.usuario.password === "") datosPersonales.usuario.password = datos.usuario.password;
        else datosPersonales.usuario.password = md5(datosPersonales.usuario.password);

        PersonalDataService.updateDatos(datosPersonales).then(() => {
            setEditing(false);
            setSuccessMessage("Se han actualizado los datos correctamente.");
            setShowSuccessDialog(true);
        }).catch(() => {
            setError("No se han podido actualizar los datos personales, intentalo de nuevo.");
        });
    }

    function onPasswordChange(e) {
        setPassword(e.target.value);
    }

    function onNombreChange(e) {
        setNombre(e.target.value);
    }

    function onApellidosChange(e) {
        setApellidos(e.target.value);
    }

    function onEmailChange(e) {
        setEmail(e.target.value);
    }

    function onEditingChange() {
        setEditing(!editing);
    }

    const hideSuccessDialog = () => {
        setShowSuccessDialog(false);
        navigate('/datosPersonales');
    };

    return (
        <div className="surface-card border-round shadow-2 p-4 m-auto" style={{width: '50%'}}>
            <div className="p-field">
                <label></label>
                <p>Datos actuales </p>
                {editing === true && <p>Datos nuevos</p>}
            </div>

            <div className="p-field">
                <label htmlFor="Usuario">Usuario</label>
                <InputText type="text" placeholder="Usuario" value={datos.usuario.username} readOnly/>
                {editing === true && <InputText type="text" placeholder={datos.usuario.username} disabled={true}
                                                value={datos.usuario.username}/>}
            </div>

            <div className="p-field">
                <label htmlFor="Contraseña">Contraseña</label>
                <InputText type="password" placeholder="password" value={datos.usuario.password} readOnly/>
                {editing === true && <InputText type="text" placeholder={"Introduce tu contraseña"} value={password}
                                                onChange={onPasswordChange}/>}
            </div>

            <div className="p-field">
                <label htmlFor="Nombre">Nombre</label>
                <InputText type="text" placeholder="nombre" value={datos.persona.nombre} readOnly/>
                {editing === true && <InputText type="text" placeholder={datos.persona.nombre} value={nombre}
                                                onChange={onNombreChange}/>}
            </div>

            <div className="p-field">
                <label htmlFor="Apellidos">Apellidos</label>
                <InputText type="text" placeholder="apellidos" value={datos.persona.apellidos} readOnly/>
                {editing === true && <InputText type="text" placeholder={datos.persona.apellidos} value={apellidos}
                                                onChange={onApellidosChange}/>}
            </div>

            <div className="p-field">
                <label htmlFor="Dni">Dni</label>
                <InputText type="text" placeholder="dni" value={datos.persona.dni} readOnly/>
                {editing === true &&
                    <InputText type="text" placeholder={datos.persona.dni} disabled={true} value={datos.persona.dni}/>}
            </div>

            <div className="p-field">
                <label htmlFor="Email">Email</label>
                <InputText type="text" placeholder="email" value={datos.persona.email} readOnly/>
                {editing === true &&
                    <InputText type="text" placeholder={datos.persona.email} value={email} onChange={onEmailChange}/>}
            </div>

            <Divider/>

            <div className="p-p-3">
                <label> </label>
                {editing === false &&
                    <div className="boton">
                        <Button label="Editar los datos" icon="pi pi-user-edit" className="p-button mr-2"
                                onClick={onEditingChange}/>
                    </div>
                }
                {editing === true &&
                    <div className="boton">
                        <Button label="Dejar de editar" icon="pi pi-times" className="p-button mr-2"
                                onClick={onEditingChange}/>
                    </div>
                }
                {editing === true &&
                    <div className="botonUpdate">
                        <Button label="Actualizar datos personales" icon="pi pi-save" className="p-button mr-2"
                                onClick={updateDatosPersonales}/>
                    </div>
                }
            </div>
            {error && <div className="error-message">{error}</div>}

            <Dialog
                visible={showSuccessDialog}
                onHide={hideSuccessDialog}
                header="Éxito"
                modal
                style={{width: '40vw'}}
                footer={
                    <div>
                        <Button label="Cerrar" onClick={hideSuccessDialog}/>
                    </div>
                }>
                <div className="success-dialog-content">
                    <p>{successMessage}</p>
                </div>
            </Dialog>
        </div>
    );
}