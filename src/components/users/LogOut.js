import Cookies from "universal-cookie";

function LogOut() {
    const cookies = new Cookies();
    cookies.remove("token");
    window.location.replace('/');
}

export default LogOut;