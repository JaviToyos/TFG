import React, {useEffect, useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import {Divider} from "primereact/divider";
import {Column} from "primereact/column";
import {DataTable} from "primereact/datatable";
import {format} from 'date-fns';
import MovimientoService from "../../services/movimientoService";
import './Movimientos.css';
import {InputText} from "primereact/inputtext";
import {Tag} from "primereact/tag";
import GoCardlessService from "../../services/goCardlessService";
import {Cookies} from "react-cookie";

const ModalMovimiento = ({cuenta}) => {
    const [visible, setVisible] = useState(false);
    const [movimientos, setMovimientos] = useState([]);
    const [filteredMovimientos, setFilteredMovimientos] = useState([]);
    const [error, setError] = useState('');
    const [filterValue, setFilterValue] = useState('');
    const [transactionIds, setTransactionIds] = useState([]);
    const [debAccounts, setDebAccounts] = useState([]);
    const [amounts, setAmounts] = useState([]);
    const [currencies, setCurrencies] = useState([]);
    const [dates, setDates] = useState([]);
    const [informations, setInformations] = useState([]);


    useEffect(() => {
        MovimientoService.buscarPorIdCuenta(cuenta.id)
            .then(res => {
                setMovimientos(res.data);
                setFilteredMovimientos(res.data);
                setError('');
            })
            .catch(() => {
                setError('No se han encontrado movimientos');
            });
    }, []);

    useEffect(() => {
        const filtered = movimientos.filter(movimiento =>
            movimiento.informacionMovimiento.toLowerCase().includes(filterValue.toLowerCase())
        );
        setFilteredMovimientos(filtered);
    }, [filterValue, movimientos]);

    useEffect(() => {
        transactionIds.map((id, index) => {
            let data = {
                movimiento : {
                    cuentaBancaria : {
                        id : cuenta.id
                    },
                    informacionMovimiento: informations[index],
                    idTransaccion: id,
                    cantidad : amounts[index],
                    divisa: currencies[index],
                    destinatario: debAccounts[index],
                    fecha: dates[index]
                }
            }
            MovimientoService.save(data)
                .then(()=> {

                });
        });
        hideDialog();
    }, [transactionIds]);

    function nombreCatBodyTemplate(rowData) {
        return rowData.categorias.map((categoria) => (
            <React.Fragment key={categoria.id}>
                <Tag value={categoria.nombre} severity={'info'}/>
                <br/>
                <br/>
            </React.Fragment>
        ));
    }

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    function verMovimientos() {
        showDialog();
    }

    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    function actualizarMovimientos() {
        let tranIds = [];
        let debAccount = [];
        let amount = [];
        let currency = [];
        let date = [];
        let information = [];

        GoCardlessService.obtenerMovimientos(getCookie('tokenGoCardless'), cuenta.accountID)
            .then((res) => {
                res.data.booked.map((transac) => {
                    tranIds.push(transac.transactionId);
                    debAccount.push(transac.debtorAccount.iban);
                    amount.push(transac.transactionAmount.amount);
                    currency.push(transac.transactionAmount.currency);
                    date.push(transac.bookingDate);
                    information.push(transac.remittanceInformationUnstructured);
                });
                setTransactionIds(tranIds);
                setDebAccounts(debAccount);
                setAmounts(amount);
                setCurrencies(currency);
                setDates(date);
                setInformations(information);
            }) .catch(() => setError('Ahora mismo no podemos obtener tus movimientos. Inténtelo de nuevo más tarde'));
    }


    return (
        <div>
            <Button id="iconMovimientos" icon="pi pi-search-plus" onClick={() => verMovimientos()}
            />
            <Dialog header={"Movimientos"} visible={visible} style={{width: '60vw'}} onHide={hideDialog}
                    footer={
                        <div>
                            <Button
                                className="p-button login-button"
                                onClick={actualizarMovimientos}
                            >
                                <span className="pi pi-sync"/>
                                <span className="iconReload">Actualizar movimientos</span>
                            </Button>
                        </div>
                    }>
                <div className="search-container">
                    <span className="p-input-icon-left">
                        <i className="pi pi-search"/>
                        <InputText
                            value={filterValue}
                            onChange={e => setFilterValue(e.target.value)}
                            placeholder="Buscar por Información"
                        />
                    </span>
                </div>
                <DataTable value={filteredMovimientos} paginator rows={3} tableStyle={{minWidth: '100%'}}>
                    <Column field="idTransaccion" header="Id de la transacción"/>
                    <Column field="destinatario" header="IBAN Destinatario"/>
                    <Column field="informacionMovimiento" header="Información" sortable/>
                    <Column field="cantidad" header="Cantidad" sortable/>
                    <Column field="divisa" header="Divisa"/>
                    <Column field="categoria" header="Categoria"
                            style={{minWidth: '12rem'}} body={(rowData) => nombreCatBodyTemplate(rowData)}/>
                    <Column
                        field="fecha"
                        header="Fecha"
                        body={(rowData) => (
                            <span>{format(new Date(rowData.fecha), 'dd-MM-yyyy')}</span>
                        )}
                    />
                </DataTable>

                {error && <Divider/> && <div className="error-message">{error}</div>}
            </Dialog>
        </div>
    );
};

export default ModalMovimiento;