import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import CriterioService from "../../services/criterioService";
import {InputText} from "primereact/inputtext";
import {Divider} from "primereact/divider";

const ModalAddCriterio = ({categoria}) => {
    const [visible, setVisible] = useState(false);
    const [criterio, setCriterio] = useState("");
    const [error, setError] = useState('');

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function onCriterioChange(e) {
        setCriterio(e.target.value);
    }

    function addCriterios() {
        const criterioSave = {
            categoria: {
                id: categoria.id
            },
            nombre: criterio
        };

        CriterioService.crear(criterioSave)
            .then(() => {
                hideDialog();
                window.location.reload();
            })
            .catch(() => setError("No se ha podido añadir el criterio"));
    }

    return (
        <div>
            <Button label="Añadir Criterios" onClick={showDialog} className="p-button mr-2"/>

            <Dialog header={"Añadir Categoria"} visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={
                        <div>
                            <Button label="Añadir" onClick={addCriterios} className="p-button-secondary"/>
                        </div>
                    }>
                <InputText type="text" placeholder={"Criterio"} value={criterio} onChange={onCriterioChange}/>

                <Divider/>

                {error && <div className="error-message">{error}</div>}

            </Dialog>
        </div>
    );
};

export default ModalAddCriterio;