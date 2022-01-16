import React from 'react';
import Users from "./Users";

const Login = () => {
    const type='Login';
    const buttonClass = 'btn btn-outline-primary';
    const url = 'http://localhost:1306/sign-in'
    return (
        <div className='all-center'>
            <Users type={type} buttonClass={buttonClass} url={url}/>
        </div>
    );
}

export default Login;