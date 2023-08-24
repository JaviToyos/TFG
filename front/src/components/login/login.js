import React, {useState, useEffect} from 'react';
import {Cookies} from "react-cookie";

import md5 from 'md5';

import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';
import {useNavigate} from "react-router-dom";

import LoginService from "../../services/loginService";
import GoCardlessService from "../../services/goCardlessService";
import './Login.css'

export default function Login({setLogged}) {
    const navigate = useNavigate();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState('');
    const [errorInputUser, setErrorInputUser] = useState('');
    const [errorInputPass, setErrorInputPass] = useState('');
    const [usernameEmpty, setUsernameEmpty] = useState(false);
    const [passwordEmpty, setPasswordEmpty] = useState(false);

    useEffect(() => {
    }, []);

    async function obtainToken() {
        try {
            const res = await GoCardlessService.obtenerToken();
            setCookie('tokenGoCardless', 'Bearer '.concat(res.data.access));
        } catch (error) {
            console.error("Error al obtener el token de GoCardless");
        }
    }

    async function iniciarSesion() {
        const loginItem = {username: username, passwd: md5(password)};
        try {
            const res = await LoginService.iniciarSesion(loginItem);
            setCookie('token', res.data.token);
            setCookie('usuario', res.data.user);
            setLogged(true);
            await obtainToken();
            navigate('/');
        } catch (error) {
            setError('El usuario o la contraseña son erróneos');
        }
    }

    function setCookie(name, data) {
        const cookies = new Cookies();
        cookies.set(name, data);
    }

    function onUsernameChange(e) {
        setUsername(e.target.value);
        setUsernameEmpty(false);
        setErrorInputUser('');
    }

    function onPasswordChange(e) {
        setPassword(e.target.value);
        setPasswordEmpty(false)
        setErrorInputPass('');
    }

    function recuperarPass() {
        navigate("recuperarPass");
    }

    const validateUser = () => {
        if (username === '') {
            setErrorInputUser('Por favor completa el campo username.');
            setUsernameEmpty(true);
        }
    };

    const validatePass = () => {
        if (password === '') {
            setErrorInputPass('Por favor completa el campo password.');
            setPasswordEmpty(true)
        }
    };

    return (
        <div className="form login">
            <div className="form-header">
                <h2>Iniciar sesión</h2>
            </div>
            <div className="form-content">
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText
                            id="username"
                            className={`inputText ${usernameEmpty ? 'empty' : ''}`}
                            value={username}
                            onChange={onUsernameChange}
                            onBlur={validateUser}
                        />
                        <label htmlFor="username">Usuario</label>
                    </span>
                </div>
                {errorInputUser && <div className="error-message">{errorInputUser}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText
                            id="password"
                            type="password"
                            className={`inputText ${passwordEmpty ? 'empty' : ''}`}
                            value={password}
                            onChange={onPasswordChange}
                            onBlur={validatePass} // Validación al cambiar el foco
                        />
                        <label htmlFor="password">Contraseña</label>
                    </span>
                </div>
                {errorInputPass && <div className="error-message">{errorInputPass}</div>}
                {error && <div className="error-message">{error}</div>}
                <Button
                    onClick={iniciarSesion}
                    className="p-button-rounded p-button-lg login-button"
                >
                    <span className="pi pi-sign-in"/>
                    <span className="iconSignIn">Iniciar sesión</span>
                </Button>
                <div className="forgot-password">
                    <Button
                        label="¿Has olvidado tu contraseña?"
                        onClick={recuperarPass}
                        className="p-button-link"
                    />
                </div>
            </div>
        </div>
    );
}