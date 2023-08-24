import React, {useState, useEffect} from 'react';
import {Dialog} from 'primereact/dialog';
import {Dropdown} from 'primereact/dropdown';
import {Button} from 'primereact/button';
import {Cookies} from "react-cookie";
import GoCardlessService from "../../services/goCardlessService";
import {MultiSelect} from "primereact/multiselect";
import CuentaBancariaService from "../../services/cuentaBancariaService";

const ModalAddCuenta = () => {
    const [dropdownOptions, setDropdownOptions] = useState([]);
    const [dropdownOptionsMulti, setDropdownOptionsMulti] = useState([]);
    const [selectedOption, setSelectedOption] = useState(null);
    const [selectedOptionId, setSelectedOptionId] = useState(null);
    const [selectedOptions, setSelectedOptions] = useState(null);
    const [visible, setVisible] = useState(false);
    const [titleHeader, setTitleHeader] = useState('');
    const [accountIds, setAccountIds] = useState([]);
    const [accountIbans, setAccountIbans] = useState([])
    const [accountNames, setAccountNames] = useState([])
    const [accountBalances, setAccountBalances] = useState([])
    const [accountCurrencies, setAccountCurrencies] = useState([])

    useEffect(() => {
        const storedOptionId = localStorage.getItem("selectedOptionId");
        if (storedOptionId) {
            setSelectedOptionId(storedOptionId);
            setSelectedOption(dropdownOptions.find(option => option.value === storedOptionId));
            loadAccounts(storedOptionId);
        }
    }, []);

    useEffect(() => {
        let ibanAcc = [];
        let nombreAcc = [];
        if (accountIds.length > 0) {
            Promise.all(
                accountIds.map(id =>
                    GoCardlessService.obtenerDetalleCuentas(getCookie('tokenGoCardless'), id)
                        .then(res => {
                            ibanAcc.push(res.data.iban);
                            nombreAcc.push(res.data.name);
                        })
                )
            )
                .then(() => {
                    setAccountIbans(ibanAcc);
                    setAccountNames(nombreAcc);
                });
        }
    }, [accountIds]);

    useEffect(() => {
        let balances = [];
        let currency = [];
        if (accountIds.length > 0) {
            Promise.all(
                accountIds.map(id =>
                    GoCardlessService.obtenerBalanceCuenta(getCookie('tokenGoCardless'), id)
                        .then(res => {
                            balances.push(res.data.balanceAmount.amount);
                            currency.push(res.data.balanceAmount.currency);
                        })
                )
            )
                .then(() => {
                    setAccountBalances(balances);
                    setAccountCurrencies(currency);
                    buildMultiSelect();
                });
        }
    }, [accountIbans]);

    const showDialog = () => {
        GoCardlessService.obtenerBancos(getCookie('tokenGoCardless'))
            .then(res => {
                let optionsFromBackend = [];
                res.data.map((item) => (
                    optionsFromBackend.push({label: item.name, value: item.id, image: item.logo})
                ));
                setDropdownOptions(optionsFromBackend);
                setVisible(true);
                setTitleHeader('Selecciona el banco del cuál obtener la cuenta');
            });
    };

    const loadAccounts = (link) => {
        let idAcc = [];
        GoCardlessService.obtenerCuentas(getCookie('tokenGoCardless'), link)
            .then(res => {
                res.data.accounts.map((cuentaId) => (
                    idAcc.push(cuentaId)
                ));
                setAccountIds(idAcc);
            });
    };


    const buildMultiSelect = () => {
        let optionsFromBackend = [];
        accountIbans.map((item, index) => (
            optionsFromBackend.push({
                label: accountNames[index], value: {
                    id: accountIds[index], iban: accountIbans[index],
                    balance: accountBalances[index], currency: accountCurrencies[index]
                }
            })
        ));
        setDropdownOptionsMulti(optionsFromBackend);
        setTitleHeader('Selecciona la cuenta o cuentas que desea añadir a la aplicación')
        setVisible(true);
    };

    const hideDialog = () => {
        setVisible(false);
    };

    const getBankLink = () => {
        setVisible(false);
        GoCardlessService.obtenerLinkBanco(getCookie('tokenGoCardless'), selectedOptionId).then(res => {
            localStorage.setItem("selectedOptionId", res.data.id);
            setDropdownOptions([]);
            window.open(res.data.link);
        });
    };

    const onOptionChange = (e) => {
        setSelectedOption(e.value);
        setSelectedOptionId(e.value);
    };

    const onOptionMultiselectChange = (e) => {
        setSelectedOptions(e.value)
    }

    const addCuenta = () => {
        selectedOptions.map((item) => (
            CuentaBancariaService.guardar(item, getCookie('usuario'))
                .then(() => {
                })
        ));
        window.location.reload();
    }

    const handleAcceptClick = () => {
        if (dropdownOptions.length > 0) {
            getBankLink();
        } else {
            addCuenta();
        }
    };

    return (
        <div>
            <Button label="Añadir cuenta" icon="pi pi-plus-circle" className="p-button mr-2" onClick={showDialog}/>

            <Dialog
                header={titleHeader}
                visible={visible}
                style={{width: '30vw'}}
                onHide={hideDialog}
                footer={
                    <div>
                        <Button label="Aceptar" onClick={handleAcceptClick}/>
                        <Button label="Cancelar" onClick={hideDialog} className="p-button-secondary"/>
                    </div>
                }>
                {dropdownOptions.length > 0 && (
                    <Dropdown
                        options={dropdownOptions}
                        value={selectedOption}
                        onChange={onOptionChange}
                        optionLabel="label"
                        placeholder="Selecciona una opción"
                    />
                )}
                {dropdownOptionsMulti.length > 0 && (
                    <MultiSelect
                        options={dropdownOptionsMulti}
                        value={selectedOption}
                        onChange={onOptionMultiselectChange}
                        optionLabel="label"
                        placeholder="Selecciona una opción"
                    />
                )}
            </Dialog>
        </div>
    );
};

function getCookie(name) {
    const cookies = new Cookies();
    return cookies.get(name);
}

export default ModalAddCuenta;