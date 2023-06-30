import React, { useState, useEffect } from 'react';

import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Dropdown } from 'primereact/dropdown';
import { Button } from 'primereact/button';
import { Divider } from 'primereact/divider';
import { classNames } from 'primereact/utils';

import { useParams, useNavigate } from "react-router-dom";


export default function CategoriaDetalle() {

    const params = useParams();
    const navigate = useNavigate();
    const esNuevo = !('idArticulo' in params);

    const articuloVacio = { id: null, nombre: "", descripcion: "", familia: { id: null, nombre: "", descripcion: "" }, precioUnitario: 0.0 };
    const [articulo, setArticulo] = useState(articuloVacio);
    const [submitted, setSubmitted] = useState(false);
    const [familias, setFamilias] = useState([]);


    useEffect(() => {
        if (!esNuevo) {
            articuloService.buscarPorId(params.idArticulo).then(res => setArticulo(res.data));
        }
        familiaService.buscarTodas().then(res => setFamilias(res.data));
    },[]); // Carga después del primer renderizado


    return (
        <div>
            {/* Contenido de Proveedor */}
        </div>
    );
}

