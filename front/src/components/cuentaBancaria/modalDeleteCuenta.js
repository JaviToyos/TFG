import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {Divider} from "primereact/divider";
import CuentaBancariaService from "../../services/cuentaBancariaService";

const ModalDeleteCuenta = ({cuenta}) => {
    const [visible, setVisible] = useState(false);
    const [error, setError] = useState("");

    const showDialog = () => {
        setError("");
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function deleteCuenta() {
        CuentaBancariaService.eliminar(cuenta.accountID)
            .then((res) => {
                if (res.status === 200) {
                    setVisible(false);
                    window.location.reload();
                }
            })
    }

    return (
        <div>
            <Button icon={"pi pi-trash"} className="p-button-raised p-button-danger" onClick={showDialog}/>

            <Dialog header={"¿Estás seguro de que deseas eliminar la cuenta?"}
                    visible={visible} style={{width: '30vw'}} onHide={hideDialog}
                    footer={<Button label={"Aceptar"} className="p-button-danger mr-2" onClick={deleteCuenta}/>}>

                {error && <Divider/> && <div className="error-message">{error}</div>}

            </Dialog>
        </div>
    );
};

export default ModalDeleteCuenta;