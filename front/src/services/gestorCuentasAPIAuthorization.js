import axios from "axios";
import {Cookies} from "react-cookie";

function getCookie(name) {
    const cookies = new Cookies();
    return cookies.get(name);
}

export default axios.create({
    baseURL: "http://localhost:8080/GestCuentas",
    withCredentials: false,
    headers: {
        "Content-type": "application/json",
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET,PUT,POST,DELETE,PATCH,OPTIONS',
        'Authorization': getCookie('token'),
    }
});