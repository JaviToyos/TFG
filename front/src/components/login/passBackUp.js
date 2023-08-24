import React, {useState, useEffect} from 'react';
import {InputText} from 'primereact/inputtext';
import {Button} from 'primereact/button';
import {useNavigate} from "react-router-dom";

import LoginService from "../../services/loginService";

export default function PassBackUp() {
    const navigate = useNavigate();

    const recuperarPassItemVacio = {username: "", email: ""};
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [recuperarPassItem, setRecuperarPassItem] = useState(recuperarPassItemVacio);
    const [error, setError] = useState('');
    const [usernameEmpty, setUsernameEmpty] = useState(false);
    const [emailEmpty, setEmailEmpty] = useState(false);
    const [errorInputUser, setErrorInputUser] = useState('');
    const [errorInputEmail, setErrorInputEmail] = useState('');


    useEffect(() => {
    }, []);

    function recuperarPass() {
        recuperarPassItemVacio.username = username;
        recuperarPassItemVacio.email = email;
        setRecuperarPassItem(recuperarPassItemVacio);
        LoginService.recuperarPass(recuperarPassItem).then(() => {
            navigate("/login/backUpOk");
        }).catch(() => {
            setError("El usuario o el email no son correctos.");
        });
    }

    function onUsernameChange(e) {
        setUsername(e.target.value);
        setUsernameEmpty(false);
        setErrorInputUser('');
    }

    function onEmailChange(e) {
        setEmail(e.target.value);
        setEmailEmpty(false)
        setErrorInputEmail('');
    }

    function login() {
        navigate("/login");
    }

    const validateUser = () => {
        if (username === '') {
            setErrorInputUser('Por favor completa el campo username.');
            setUsernameEmpty(true);
        }
    };

    const validateEmail = () => {
        if (email === '') {
            setErrorInputEmail('Por favor completa el campo email.');
            setEmailEmpty(true)
        }
    };

    return (
        <div className="form passBackup">
            <div className="form-header">
                <h2>Recuperar contraseña</h2>
            </div>
            <div className="form-content">
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="username"
                                   className={`inputText ${usernameEmpty ? 'empty' : ''}`}
                                   value={username}
                                   onChange={onUsernameChange}
                                   onBlur={validateUser}/>
                        <label htmlFor="username">Usuario</label>
                    </span>
                </div>
                {errorInputUser && <div className="error-message">{errorInputUser}</div>}
                <div className="input-field">
                    <span className="p-float-label">
                        <InputText id="email" type="text"
                                   className={`inputText ${emailEmpty ? 'empty' : ''}`}
                                   value={email}
                                   onChange={onEmailChange}
                                   onBlur={validateEmail}/>
                        <label htmlFor="email">Email</label>
                    </span>
                </div>
                {errorInputEmail && <div className="error-message">{errorInputEmail}</div>}
                {error && <div className="error-message">{error}</div>}
                <div className="form-buttons">
                    <Button onClick={recuperarPass} className="p-button-rounded p-button-lg login-button">
                        <span className="pi pi-sync"/>
                        <span className="iconSignIn">Recuperar contraseña</span>
                    </Button>
                </div>
            </div>
            <Button onClick={login} className="p-button-rounded p-button passbackup-button">
                <span className="pi pi-chevron-left"/>
            </Button>
        </div>
    );
}