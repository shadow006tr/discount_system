import Cookies from "universal-cookie";
import React from "react";

function LogOut() {
    const cookies = new Cookies();
    cookies.remove("token");
    window.location.replace('/');
}

export default LogOut;