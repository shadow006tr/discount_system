import React from 'react';
import Users from "../pages/Users";

const Login = () => {
    const type='Login';
    const buttonClass = 'btn btn-outline-primary mt-4';
    const url = 'http://localhost:8989/sign-in'
    return (
        <div className='all-center'>
            <Users type={type} buttonClass={buttonClass} url={url}/>
        </div>
    );
}

export default Login;