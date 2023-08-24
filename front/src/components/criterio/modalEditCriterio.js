import React, {useEffect, useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {InputText} from "primereact/inputtext";
import CriterioService from "../../services/criterioService";

const ModalEditCriterio = ({crit}) => {
    const [visible, setVisible] = useState(false);
    const [criterio, setCriterio] = useState("");
    const [error, setError] = useState('');

    useEffect(() => {
        setCriterio(crit.nombre)
    }, []);

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function onCriterioChange(e) {
        setCriterio(e.target.value);
    }

    function modificarCriterio() {
        const data = {
            id: crit.id,
            nombre: criterio
        }

        CriterioService.modificar(data)
            .then(() => {
                hideDialog();
                window.location.reload();
            }).catch(() => setError("No se ha podido modificar el criterio"));
    }

    return (
        <div>
            <Button icon={"pi pi-pencil"} onClick={showDialog} className="p-button"/>
            <Dialog header={"Modificar criterio"} visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={
                        <div>
                            <Button label="Modificar" onClick={modificarCriterio} className="p-button"/>
                        </div>
                    }>

                <InputText type="text" value={criterio} onChange={onCriterioChange}/>

                {error && <div className="error-message">{error}</div>}
            </Dialog>
        </div>
    );
};

export default ModalEditCriterio;