import React from 'react';
import Users from "./Users";

const SignUp = () => {
    const type='Sign-Up';
    const buttonClass = 'btn btn-outline-success';
    const url = 'http://localhost:1306/create-account'

    return (
        <div className='all-center'>
            <Users type={type} buttonClass={buttonClass} url={url}/>
        </div>
    );
}

export default SignUp;