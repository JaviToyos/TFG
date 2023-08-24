import React, {useState, useEffect} from 'react';
import {Cookies} from "react-cookie";

import {Divider} from 'primereact/divider';

import CuentaBancariaService from "../../services/cuentaBancariaService";
import "./CuentaBancaria.css";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import ModalDeleteCuenta from "./modalDeleteCuenta";
import ModalAddCuenta from "./modalAddCuenta";
import ModalMovimiento from "../movimiento/modalMovimiento";

export default function CuentaBancaria() {
    const [datosCuentas, setDatosCuentas] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        CuentaBancariaService.buscarCuentas(getCookie("usuario"))
            .then(res => setDatosCuentas(res.data))
            .catch(() => {
                setError("No se han encontrado cuentas bancarias.");
            });
    }, []);


    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    return (
        <div className="surface-card border-round shadow-2 p-4 m-auto"
             style={{width: '100%', maxWidth: '800px', margin: '0 auto'}}>
            <div className="p-field-p">
                <p>Cuentas Bancarias</p>
            </div>

            <div style={{flex: '1 1 auto'}}>
                <DataTable value={datosCuentas} tableStyle={{minWidth: '100%'}}>
                    <Column field="nombre" header="Nombre cuenta"/>
                    <Column field="iban" header="IBAN"/>
                    <Column field="moneda" header="Moneda"/>
                    <Column field="cantidad" header="Balance"/>
                    <Column body={(rowData) => (<ModalMovimiento cuenta={rowData}/>)}/>
                    <Column
                        body={(rowData) => (
                            <ModalDeleteCuenta cuenta={rowData}/>

                        )}
                    />
                    <Column field="accountId" hidden={true}/>
                    <Column field="id" hidden={true}/>
                </DataTable>

                <Divider/>

                {error && <div className="error-message">{error}</div>}

                <ModalAddCuenta/>
            </div>
        </div>
    );
}