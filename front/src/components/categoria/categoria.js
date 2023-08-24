import React, {useState, useEffect} from 'react';
import CategoriaService from "../../services/categoriaService";
import CriterioService from "../../services/criterioService";
import "./Categoria.css";
import ModalCriterios from "../criterio/modalCriterios";
import ModalAddCategoria from "../categoria/modalAddCategoria";
import ModalDeleteCategoria from "../categoria/modalDeleteCategoria";
import {Cookies} from "react-cookie";
import {Divider} from "primereact/divider";
import {DataTable} from "primereact/datatable";
import {Column} from "primereact/column";
import ModalEditCategoria from "./modalEditCategoria";

export default function Categoria() {
    const [categoriaList, setCategoriaList] = useState([]);
    const [criterioList, setCriterioList] = useState([]);

    useEffect(() => {
        CategoriaService.buscarPorUsuario(getCookie("usuario"))
            .then(res => setCategoriaList(res.data));
    }, []);

    useEffect(() => {
        if (categoriaList.length > 0) {
            Promise.all(
                categoriaList.map(categoria =>
                    CriterioService.buscarPorCategoria(categoria.id)
                        .then(criterioResult => criterioResult.data)
                )
            )
                .then(criterioLists => setCriterioList(criterioLists.flat()));
        }
    }, [categoriaList]);

    function getCookie(name) {
        const cookies = new Cookies();
        return cookies.get(name);
    }

    return (
        <div className="surface-card border-round shadow-2 p-4 m-auto" style={{width: '35%'}}>
            <div className="p-field-p">
                <p>Categorias</p>
            </div>

            <div style={{flex: '1 1 auto'}}>
                <DataTable value={categoriaList}>
                    <Column field="id" hidden={true}/>
                    <Column field="nombre" header="Nombre"
                            style={{width: 'auto', flexGrow: 1, overflowWrap: 'break-word'}}/>
                    <Column body={(rowData) => <ModalCriterios categoria={rowData} criterioList={criterioList}/>}
                            style={{width: '10px'}}/>
                    <Column body={(rowData) => <ModalEditCategoria cat={rowData}/>}
                            style={{width: '10px'}}/>
                    <Column body={(rowData) => <ModalDeleteCategoria categoria={rowData}/>}
                            style={{width: '10px'}}/>
                </DataTable>

                <Divider/>

                <ModalAddCategoria/>
            </div>
        </div>

    );

}
