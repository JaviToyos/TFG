import {
  BrowserRouter,
  Routes,
  Route,
  NavLink
} from "react-router-dom";

import Home from './components/home';

import Login from "./components/login/login";
import RecuperarPass from "./components/login/passBackUp";

import CategoriaListado from "./components/categoria/categoriaListado";
import CategoriaDetalle from "./components/categoria/categoriaDetalle";

import CategoriaMovimientoListado from "./components/categoriaMovimiento/categoriaMovimientoListado";
import CategoriaMovimientoDetalle from "./components/categoriaMovimiento/categoriaMovimientoDetalle";

import MovimientoListado from "./components/movimiento/movimientoListado";
import MovimientoDetalle from "./components/movimiento/movimientoDetalle";

import CriterioListado from "./components/criterio/criterioListado";
import CriterioDetalle from "./components/criterio/criterioDetalle";

import CuentaBancariaListado from "./components/cuentaBancaria/cuentaBancariaListado";
import CuentaBancariaDetalle from "./components/cuentaBancaria/cuentaBancariaDetalle";

import GoCardlessListado from "./components/goCardless/goCardlessListado";
import GoCardlessDetalle from "./components/goCardless/goCardlessDetalle";

import PersonaListado from "./components/persona/personaListado";
import PersonaDetalle from "./components/persona/personaDetalle";

import ProveedorListado from "./components/proveedor/proveedorListado";
import ProveedorDetalle from "./components/proveedor/proveedorDetalle";

import UsuarioListado from "./components/usuario/usuarioListado";
import UsuarioDetalle from "./components/usuario/usuarioDetalle";


import 'primereact/resources/themes/saga-blue/theme.css';
import 'primereact/resources/primereact.min.css';
import 'primeicons/primeicons.css';
import 'primeflex/primeflex.css';
//import './App.css';

function App() {
  return (
    <div>

      <BrowserRouter forceRefresh>

        <nav className="flex">
          <NavLink to="/" className="px-5 py-3 no-underline text-900 text-xl border-bottom-2 border-300 hover:border-500">Home</NavLink>
          <NavLink to="/login" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">Login</NavLink>
          <NavLink to="/categoria" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">categoria</NavLink>
          <NavLink to="/categoriaMovimiento" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">categoriaMovimiento</NavLink>
          <NavLink to="/criterio" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">criterio</NavLink>
          <NavLink to="/cuentaBancaria" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">cuentaBancaria</NavLink>
          <NavLink to="/goCardless" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">goCardless</NavLink>
          <NavLink to="/movimiento" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">movimiento</NavLink>
          <NavLink to="/persona" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">persona</NavLink>
          <NavLink to="/proveedor" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">proveedor</NavLink>
          <NavLink to="/usuario" className="px-5 py-3 no-underline text-700 text-xl border-bottom-2 border-300 hover:border-500">usuario</NavLink>
        </nav>

        <div className="p-5">
          <Routes>
            <Route path="/" element={<Home mensaje="Página principal" />} />
            <Route path="categoria" >
              <Route index element={<CategoriaListado />} />
              <Route path="nuevo" element={<CategoriaDetalle />} />
              <Route path=":idCategoria" element={<CategoriaDetalle />} />
            </Route>

            <Route path="categoriaMovimiento" >
              <Route index element={<CategoriaMovimientoListado />} />
              <Route path="nuevo" element={<CategoriaMovimientoDetalle />} />
              <Route path=":idCategoriaMovimiento" element={<CategoriaMovimientoDetalle />} />
            </Route>

            <Route path="criterio" >
              <Route index element={<CriterioListado />} />
              <Route path="nuevo" element={<CriterioDetalle />} />
              <Route path=":idCriterio" element={<CriterioDetalle />} />
            </Route>

            <Route path="cuentaBancaria" >
              <Route index element={<CuentaBancariaListado />} />
              <Route path="nuevo" element={<CuentaBancariaDetalle />} />
              <Route path=":idCuentaBancaria" element={<CuentaBancariaDetalle />} />
            </Route>

            <Route path="goCardless" >
              <Route index element={<GoCardlessListado />} />
              <Route path="nuevo" element={<GoCardlessDetalle  />} />
              <Route path=":idGoCardless" element={<GoCardlessDetalle />} />
            </Route>

            <Route path="movimiento" >
              <Route index element={<MovimientoListado />} />
              <Route path="nuevo" element={<MovimientoDetalle />} />
              <Route path=":idMovimiento" element={<MovimientoDetalle />} />
            </Route>

            <Route path="persona" >
              <Route index element={<PersonaListado />} />
              <Route path="nuevo" element={<PersonaDetalle />} />
              <Route path=":idPersona" element={<PersonaDetalle />} />
            </Route>

            <Route path="proveedor" >
              <Route index element={<ProveedorListado />} />
              <Route path="nuevo" element={<ProveedorDetalle />} />
              <Route path=":idProveedor" element={<ProveedorDetalle />} />
            </Route>

            <Route path="usuario" >
              <Route index element={<UsuarioListado mensaje="Vista de usuarios" />} />
              <Route path="nuevo" element={<UsuarioDetalle />} />
              <Route path=":idUsuario" element={<UsuarioDetalle />} />
            </Route>

            <Route path="login" >
              <Route index element={<Login />} />
              <Route path="recuperarPass" element={<RecuperarPass/>} />
            </Route>

          </Routes>
        </div>

      </BrowserRouter >
    </div >
  );
}

export default App;
