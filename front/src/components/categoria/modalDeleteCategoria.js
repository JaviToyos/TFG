import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import CategoriaService from "../../services/categoriaService";
import {Divider} from "primereact/divider";

const ModalDeleteCategoria = ({categoria}) => {
    const [visible, setVisible] = useState(false);
    const [error, setError] = useState("");

    const showDialog = () => {
        setError("");
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    const deleteCategoria = () => {
        CategoriaService.eliminar(categoria.id)
            .then(() => {
                setVisible(false);
                window.location.reload();
            })
            .catch(() => setError("No se ha podido eliminar la categoria"));
    };

    return (
        <div>
            <Button icon={"pi pi-trash"} className="p-button-danger" onClick={showDialog}/>

            <Dialog header={"¿Estás seguro de que deseas eliminar la categoria?"}
                    visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={<Button label={"Aceptar"} className="p-button-danger mr-2" onClick={deleteCategoria}/>}>

                {error && <Divider/> && <div className="error-message">{error}</div>}

            </Dialog>
        </div>
    );
};

export default ModalDeleteCategoria;