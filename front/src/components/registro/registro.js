import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {InputText} from "primereact/inputtext";
import {Button} from "primereact/button";
import './Registro.css';
import md5 from "md5";
import RegistroService from "../../services/registroService";
import {Dialog} from "primereact/dialog";

export default function Registro() {
    const navigate = useNavigate();

    const [dni, setDni] = useState("");
    const [nombre, setNombre] = useState("");
    const [apellidos, setApellidos] = useState("");
    const [email, setEmail] = useState("");
    const [dniEmpty, setDniEmpty] = useState(false);
    const [nombreEmpty, setNombreEmpty] = useState(false);
    const [apellidosEmpty, setApellidosEmpty] = useState(false);
    const [emailEmpty, setEmailEmpty] = useState(false);
    const [username, setUsername] = useState("");
    const [passwd1, setPasswd1] = useState("");
    const [passwd2, setPasswd2] = useState("");
    const [usernameEmpty, setUsernameEmpty] = useState(false);
    const [passwd1Empty, setPasswd1Empty] = useState(false);
    const [passwd2Empty, setPasswd2Empty] = useState(false);
    const [error, setError] = useState("");
    const [errorInputDNI, setErrorInputDNI] = useState("");
    const [errorInputNombre, setErrorInputNombre] = useState("");
    const [errorInputApellidos, setErrorInputApellidos] = useState("");
    const [errorInputEmail, setErrorInputEmail] = useState("");
    const [errorInputUsername, setErrorInputUsername] = useState("");
    const [errorInputPasswd1, setErrorInputPasswd1] = useState("");
    const [errorInputPasswd2, setErrorInputPasswd2] = useState("");
    const [showSuccessDialog, setShowSuccessDialog] = useState(false);
    const [successMessage, setSuccessMessage] = useState('');

    useEffect(() => {
    }, []);

    function registrar() {
        if (passwd1 !== passwd2) {
            setError('Las contraseñas no coinciden. ')
            return;
        }

        const registro = {
            usuario: {
                username: username,
                password: md5(passwd1)
            },
            persona: {
                dni: dni,
                nombre: nombre,
                apellidos: apellidos,
                email: email
            }
        }

        RegistroService.registrar(registro)
            .then(() => {
                setSuccessMessage('El usuario se ha registrado correctamente');
                setShowSuccessDialog(true);
            }).catch(() => {
            setError("Error al registrar el usuario. Por favor, revisa los datos");
        });
    }

    const hideSuccessDialog = () => {
        setShowSuccessDialog(false);
        navigate('/login');
    };

    function login() {
        navigate("/login");
    }

    function onDniChange(e) {
        setDni(e.target.value);
        setDniEmpty(false);
        setErrorInputDNI('');
    }

    function onNombreChange(e) {
        setNombre(e.target.value);
        setNombreEmpty(false)
        setErrorInputNombre('');
    }

    function onApellidosChange(e) {
        setApellidos(e.target.value);
        setApellidosEmpty(false);
        setErrorInputApellidos('');
    }

    function onEmailChange(e) {
        setEmail(e.target.value);
        setEmailEmpty(false)
        setErrorInputEmail('');
    }

    function onUsernameChange(e) {
        setUsername(e.target.value);
        setUsernameEmpty(false)
        setErrorInputUsername('');
    }

    function onPasswd1Change(e) {
        setPasswd1(e.target.value);
        setPasswd1Empty(false);
        setErrorInputPasswd1('');
    }

    function onPasswd2Change(e) {
        setPasswd2(e.target.value);
        setPasswd2Empty(false);
        setErrorInputPasswd2('');
    }

    const validateDNI = () => {
        if (dni === '') {
            setErrorInputDNI('Por favor completa el campo dni.');
            setDniEmpty(true)
        }

        if (dni.length > 9) {
            setErrorInputDNI('El DNI tiene más de 9 caracteres');
            setDniEmpty(true)
        }
    };

    const validateNombre = () => {
        if (nombre === '') {
            setErrorInputNombre('Por favor completa el campo nombre.');
            setNombreEmpty(true)
        }
    };

    const validateApellidos = () => {
        if (dni === '') {
            setErrorInputApellidos('Por favor completa el campo apellidos.');
            setApellidosEmpty(true);
        }
    };

    const validateEmail = () => {
        if (email === '') {
            setErrorInputEmail('Por favor completa el campo email.');
            setEmailEmpty(true)
        }
    };

    const validateUsername = () => {
        if (username === '') {
            setErrorInputUsername('Por favor completa el campo username.');
            setUsernameEmpty(true)
        }
    };

    const validatePasswd1 = () => {
        if (passwd1 === '') {
            setErrorInputPasswd1('Por favor completa el campo contraseña.');
            setPasswd1Empty(true)
        }
    };

    const validatePasswd2 = () => {
        if (passwd2 === '') {
            setErrorInputPasswd2('Por favor completa el campo contraseña.');
            setPasswd2Empty(true)
        }
    };

    return (
        <div className="form registro">
            <div className="form-header">
                <h2>Registrarse</h2>
            </div>
            <div className="form-content">
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="dni"
                                   className={`inputText ${dniEmpty ? 'empty' : ''}`}
                                   value={dni}
                                   onChange={onDniChange}
                                   onBlur={validateDNI}
                        />
                        <label htmlFor="dni">DNI</label>
                    </span>
                </div>
                {errorInputDNI && <div className="error-message">{errorInputDNI}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="nombre"
                                   className={`inputText ${nombreEmpty ? 'empty' : ''}`}
                                   value={nombre}
                                   onChange={onNombreChange}
                                   onBlur={validateNombre}
                        />
                        <label htmlFor="nombre">Nombre</label>
                    </span>
                </div>
                {errorInputNombre && <div className="error-message">{errorInputNombre}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="apellidos"
                                   className={`inputText ${apellidosEmpty ? 'empty' : ''}`}
                                   value={apellidos}
                                   onChange={onApellidosChange}
                                   onBlur={validateApellidos}
                        />
                        <label htmlFor="apellidos">Apellidos</label>
                    </span>
                </div>
                {errorInputApellidos && <div className="error-message">{errorInputApellidos}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="email"
                                   className={`inputText ${emailEmpty ? 'empty' : ''}`}
                                   value={email}
                                   onChange={onEmailChange}
                                   onBlur={validateEmail}
                        />
                        <label htmlFor="email">Email</label>
                    </span>
                </div>
                {errorInputEmail && <div className="error-message">{errorInputEmail}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="username"
                                   className={`inputText ${usernameEmpty ? 'empty' : ''}`}
                                   value={username}
                                   onChange={onUsernameChange}
                                   onBlur={validateUsername}
                        />
                        <label htmlFor="username">Username</label>
                    </span>
                </div>
                {errorInputUsername && <div className="error-message">{errorInputUsername}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="passwd1"
                                   type="password"
                                   className={`inputText ${passwd1Empty ? 'empty' : ''}`}
                                   value={passwd1}
                                   onChange={onPasswd1Change}
                                   onBlur={validatePasswd1}
                        />
                        <label htmlFor="passwd1">Contraseña</label>
                    </span>
                </div>
                {errorInputPasswd1 && <div className="error-message">{errorInputPasswd1}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="passwd2"
                                   type="password"
                                   className={`inputText ${passwd2Empty ? 'empty' : ''}`}
                                   value={passwd2}
                                   onChange={onPasswd2Change}
                                   onBlur={validatePasswd2}
                        />
                        <label htmlFor="passwd2">Repita la contraseña</label>
                    </span>
                </div>
                {errorInputPasswd2 && <div className="error-message">{errorInputPasswd2}</div>}
                {error && <div className="error-message">{error}</div>}
                <div className="form-buttons">
                    <Button onClick={registrar} className="p-button-rounded p-button-lg login-button">
                        <span className="pi pi-user-plus"/>
                        <span className="iconSignUp">Registrarse</span>
                    </Button>
                </div>
            </div>
            <Button onClick={login} className="p-button-rounded p-button passbackup-button">
                <span className="pi pi-chevron-left"/>
            </Button>
            <Dialog
                visible={showSuccessDialog}
                onHide={hideSuccessDialog}
                header="Éxito"
                modal
                style={{width: '40vw'}}>
                <div className="success-dialog-content">
                    <p>{successMessage}</p>
                    <Button label="Cerrar" onClick={hideSuccessDialog}/>
                </div>
            </Dialog>
        </div>
    );
}