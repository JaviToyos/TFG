import React, {useEffect} from "react";

import {Cookies} from "react-cookie";
import {useNavigate} from "react-router-dom";

const Logout = ({setLogged}) => {
    const navigate = useNavigate();
    const cookies = new Cookies();

    cookies.set('token', "");
    cookies.set('usuario', "");
    cookies.set('tokenGoCardless', "");

    setLogged(false)

    useEffect(() => {
        navigate("/login");
    }, []);
    console.log("Usuario cerró sesión.");
};

export default Logout;