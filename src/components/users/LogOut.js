import Cookies from "universal-cookie";

function LogOut() {
    const cookies = new Cookies();
    cookies.remove("logged_in");
    window.location.reload();
}

export default LogOut;