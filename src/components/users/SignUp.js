import React from 'react';
import Users from "../pages/Users";

const SignUp = () => {
    const type='Sign-Up';
    const buttonClass = 'btn btn-outline-success mt-4';
    const url = 'http://localhost:8989/create-account'

    return (
        <div className='all-center'>
            <Users type={type} buttonClass={buttonClass} url={url}/>
        </div>
    );
}

export default SignUp;