import React, {useRef} from 'react';
import { NavLink } from 'react-router-dom';
import { Menubar } from 'primereact/menubar';
import {Cookies} from "react-cookie";
import {Button} from "primereact/button";
import {OverlayPanel} from "primereact/overlaypanel";
import './Navbar.css';

const Navbar = ({ logged }) => {

    const overlayRef = useRef(null);

    const items = logged
        ? [
            {
                label: 'Home',
                icon: 'pi pi-fw pi-home',
                url: '/'
            },
            {
                label: 'Cuenta Bancaria',
                icon: 'pi pi-fw pi-credit-card',
                url: '/cuentaBancaria'
            },
            {
                label: 'Categorias',
                icon: 'pi pi-fw pi-list',
                url: '/categorias'
            }
        ]
        : [
            {
                label: 'Home',
                icon: 'pi pi-fw pi-home',
                url: '/'
            },
            {
                label: 'Login',
                icon: 'pi pi-fw pi-sign-in',
                url: '/login'
            },
            {
                label: 'Registrarse',
                icon: 'pi pi-fw pi-user-plus',
                url: '/registro'
            }
        ];

    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    const showOverlay = (event) => {
        overlayRef.current.toggle(event);
    };

    const buildUserMenu = () => {
        let usuario = getCookie('usuario');
        return (
            <div className="user-menu">
                <div>
                    <Button
                        className="p-button p-ml-2 pi pi-user p-button-text p-button-rounded"
                        onClick={showOverlay}
                    />
                    <div className="divNameUser">
                        <span className="usernameClass">{usuario}</span>
                    </div>
                </div>
                <OverlayPanel ref={overlayRef} appendTo={document.body}>
                    <div className="overlay-content">
                        <div className="overlay-row">
                            <NavLink to="/datosPersonales" className="overlay-link">
                                <span className="overlay-icon pi pi-user-edit" />
                                <span className="overlay-label">Datos personales</span>
                            </NavLink>
                        </div>
                        <div className="overlay-row">
                            <NavLink to="/logout" className="overlay-link">
                                <span className="overlay-icon pi pi-power-off" />
                                <span className="overlay-label">Logout</span>
                            </NavLink>
                        </div>
                    </div>
                </OverlayPanel>
            </div>
        );

    }
    return (
        <Menubar
            model={items}
            end={logged ? buildUserMenu(): ''}
        />
    );
};

export default Navbar;