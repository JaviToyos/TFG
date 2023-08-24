import React, {useEffect, useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {InputText} from "primereact/inputtext";
import CategoriaService from "../../services/categoriaService";

const ModalEditCategoria = ({cat}) => {
    const [visible, setVisible] = useState(false);
    const [categoria, setCategoria] = useState("");
    const [error, setError] = useState('');

    useEffect(() => {
        setCategoria(cat.nombre)
    }, []);

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function onCategoriaChange(e) {
        setCategoria(e.target.value);
    }

    function modificarCategoria() {
        const data = {
            idCategoria: cat.id,
            nombre: categoria
        }

        CategoriaService.modificar(data)
            .then(() => {
                hideDialog();
                window.location.reload();
            }).catch(() => setError("No se ha podido modificar la categoria"));
    }

    return (
        <div>
            <Button icon={"pi pi-pencil"} onClick={showDialog} className="p-button"/>
            <Dialog header={"Modificar categoria"} visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={
                        <div>
                            <Button label="Modificar" onClick={modificarCategoria} className="p-button"/>
                        </div>
                    }>

                <InputText type="text" value={categoria} onChange={onCategoriaChange}/>

                {error && <div className="error-message">{error}</div>}
            </Dialog>
        </div>
    );
};

export default ModalEditCategoria;