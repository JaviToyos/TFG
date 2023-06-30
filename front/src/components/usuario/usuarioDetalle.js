import React, { useState, useEffect } from 'react';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';
import { useParams, useNavigate } from "react-router-dom";

import usuarioService from '../../services/usuarioService';

export default function UsuarioDetalle() {
    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idArticulo' in params);

    const usuarioVacio = { id: null, nombre: "", descripcion: "", familia: { id: null, nombre: "", descripcion: "" }, precioUnitario: 0.0 };
    const [usuario, setUsuario] = useState(usuarioVacio);
    const [submitted, setSubmitted] = useState(false);
    const [familias, setFamilias] = useState([]);

    useEffect(() => {
        if (!esNuevo) {
            usuarioService.buscarPorId(params.idArticulo).then(res => setUsuario(res.data));
        }
        familiaService.buscarTodas().then(res => setFamilias(res.data));
    },[]); // Carga después del primer renderizado

    return (
        <div>
            {/* Contenido de Proveedor */}
        </div>
    );
}