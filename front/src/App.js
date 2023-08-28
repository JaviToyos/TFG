import {useState} from "react";
import {
    BrowserRouter,
    Routes,
    Route
} from "react-router-dom";

import Home from './components/home';
import Navbar from './components/Navbar';

import Login from "./components/login/login";
import RecuperarPass from "./components/login/passBackUp";
import Logout from "./components/login/logout";
import Registro from "./components/registro/registro";

import DatosPersonales from "./components/datosPersonales/datosPersonales";

import CuentaBancaria from "./components/cuentaBancaria/cuentaBancaria";

import Categoria from "./components/categoria/categoria";

import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
import {Cookies} from "react-cookie";

function App() {
    const [logged, setLogged] = useState(getCookie());

    function getCookie() {
        const cookies = new Cookies();

        if (cookies.get("usuario") !== null) {
            return cookies.get("usuario") !== "";
        }
        return false;
    }

    return (
        <div>
            <BrowserRouter forceRefresh>

                <Navbar logged={logged}/>

                <div className="p-5">
                    <Routes>
                        <Route path="/" element={<Home mensaje="Página principal"/>}/>

                        <Route path="login">
                            <Route index element={<Login setLogged={setLogged}/>}/>
                            <Route path="recuperarPass" element={<RecuperarPass/>}/>
                        </Route>

                        <Route path="logout" element={<Logout setLogged={setLogged}/>}/>

                        <Route path="registro">
                            <Route index element={<Registro/>}/>
                        </Route>

                        <Route path="datosPersonales">
                            <Route index element={<DatosPersonales/>}/>
                        </Route>

                        <Route path="cuentaBancaria">
                            <Route index element={<CuentaBancaria/>}/>
                        </Route>

                        <Route path="categoria">
                            <Route index element={<Categoria/>}/>
                        </Route>

                    </Routes>
                </div>

            </BrowserRouter>
        </div>
    );
}

export default App;
