import React, {useState} from 'react';
import {Dialog} from 'primereact/dialog';
import {Button} from 'primereact/button';
import ModalAddCriterio from "../criterio/modalAddCriterio";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import ModalDeleteCriterio from "./modalDeleteCriterio";
import ModalEditCriterio from "./modalEditCriterio";

const ModalCriterios = ({categoria, criterioList}) => {
    const [visible, setVisible] = useState(false);
    const filteredCriterioList = criterioList.filter(criterio => criterio.categoria.id === categoria.id);

    const showDialog = () => {
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    return (
        <div>
            <Button label="Criterios" className="p-button" onClick={showDialog}/>

            <Dialog header={"Criterios"} visible={visible} style={{width: '30vw', textAlign: 'center'}}
                    onHide={hideDialog} footer={<ModalAddCriterio categoria={categoria}/>}>
                <div style={{display: 'inline-block', textAlign: 'left', width: '100%'}}>
                    <DataTable value={filteredCriterioList} style={{margin: '0 auto'}}>
                        <Column field="nombre" header="Nombre"/>
                        <Column body={(rowData) => (<ModalEditCriterio crit={rowData}/>)}
                                style={{width: '10%', textAlign: 'center'}}/>
                        <Column body={(rowData) => (<ModalDeleteCriterio criterio={rowData}/>)}
                                style={{width: '10%', textAlign: 'center'}}/>
                    </DataTable>
                </div>
            </Dialog>

        </div>
    );
};

export default ModalCriterios;