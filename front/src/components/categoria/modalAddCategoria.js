import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {InputText} from "primereact/inputtext";
import {Divider} from "primereact/divider";
import CategoriaService from "../../services/categoriaService";
import {Cookies} from "react-cookie";

const ModalAddCategoria = () => {
    const [visible, setVisible] = useState(false);
    const [categoria, setCategoria] = useState("");
    const [error, setError] = useState('');

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function onCategoriaChange(e) {
        setCategoria(e.target.value);
    }

    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    function addCategoria() {
        const categoriaToSend = {
            usuario: {
                username: getCookie("usuario")
            },
            nombre: categoria
        };

        CategoriaService.crear(categoriaToSend)
            .then(() => {
                hideDialog();
                window.location.reload();
            })
            .catch(() => setError("No se ha podido añadir la categoria"));
    }

    return (
        <div>
            <Button label="Añadir categoria" icon="pi pi-plus-circle" className="p-button mr-2" onClick={showDialog}/>

            <Dialog header={"Criterios"} visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={
                        <div>
                            <Button label="Añadir" onClick={addCategoria} className="p-button mr-2"/>
                        </div>
                    }>
                <InputText type="text" placeholder={"Categoria"} value={categoria} onChange={onCategoriaChange}/>

                <Divider/>

                {error && <div className="error-message">{error}</div>}

            </Dialog>
        </div>
    );
};

export default ModalAddCategoria;