import React, { useState, useEffect } from 'react';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { ProgressSpinner } from 'primereact/progressspinner';
import { Dialog } from 'primereact/dialog';
import { Dropdown } from 'primereact/dropdown';
import { useNavigate } from 'react-router-dom';


export default function GoCardlessListado(props) {
    const [textoBusqueda, setTextoBusqueda] = useState("");
    const [familias, setFamilias] = useState([]);
    const [familiaBusqueda, setFamiliaBusqueda] = useState(null);
    const [articulos, setArticulos] = useState(null);
    const [cargando, setCargando] = useState(true);
    const [articuloActual, setArticuloActual] = useState(null);
    const [dialogoBorrado, setDialogoBorrado] = useState(false);

    let navigate = useNavigate();

    useEffect(() => {
        articuloService.buscarTodos().then(res => {
            setArticulos(res.data);
            setCargando(false);
        });
    }, [dialogoBorrado]); // vincula la recarga a cambios en dialogoBorrado (para forzar la recarga despues de un borrado)

    useEffect(() => {
        familiaService.buscarTodas().then(res => setFamilias(res.data));
    }, []);  // al no estar vinculado a cambios, solo se ejecuta en el primer renderizado

    return (
        <div>
            {/* Contenido de Proveedor */}
        </div>
    );
}