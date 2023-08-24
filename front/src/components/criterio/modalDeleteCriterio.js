import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {Divider} from "primereact/divider";
import CriterioService from "../../services/criterioService";

const ModalDeleteCriterio = ({criterio}) => {
    const [visible, setVisible] = useState(false);
    const [error, setError] = useState("");

    const showDialog = () => {
        setError("");
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    const deleteCriterio = () => {
        CriterioService.eliminar(criterio.id)
            .then(() => {
                setVisible(false);
                window.location.reload();
            })
            .catch(() => setError("No se ha podido eliminar el criterio"));
    };

    return (
        <div>
            <Button icon={"pi pi-trash"} className="p-button-danger" onClick={showDialog}/>

            <Dialog header={"¿Estás seguro de que deseas eliminar el criterio?"}
                    visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={<Button label={"Aceptar"} className="p-button-danger mr-2" onClick={deleteCriterio}/>}>

                {error && <Divider/> && <div className="error-message">{error}</div>}

            </Dialog>
        </div>
    );
};

export default ModalDeleteCriterio;