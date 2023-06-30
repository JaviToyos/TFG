import React, { useState, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';
import { useParams, useNavigate } from "react-router-dom";

import proveedorService from '../../services/proveedorService';
import cuentaBancariaService from "../../services/cuentaBancariaService";
import goCardlessService from "../../services/goCardlessService";

export default function ProveedorDetalle() {
    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idProveedor' in params);

    const proveedorVacio = { id: null, nombre: "",  cuentaBancaria: { id: null, nombre: "", descripcion: "" },  goCardless: { id: null, nombre: "", descripcion: "" } };
    const [proveedor, setProveedor] = useState(proveedorVacio);
    const [submitted, setSubmitted] = useState(false);
    const [cuentasBancarias, setCuentasBancarias] = useState([]);
    const [goCardless, setGoCardless] = useState([]);

    useEffect(() => {
        if (!esNuevo) {
            proveedorService.buscarPorId(params.idProveedor).then(res => setProveedor(res.data));
        }
        cuentaBancariaService.buscarTodas().then(res => setCuentasBancarias(res.data));
        goCardlessService.buscarTodas().then(res => setGoCardless(res.data));
    },[]); // Carga después del primer renderizado

    return (
        <div>
            {/* Contenido de Proveedor */}
        </div>
    );
}