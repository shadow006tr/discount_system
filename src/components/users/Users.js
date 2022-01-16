import React, {useContext, useState} from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import Cookies from 'universal-cookie';

import AlertContext from '../../context/alert/alertContext';

const Users = (props) => {
    const type = props.type;
    const buttonClass = props.buttonClass;
    const url = props.url;


    const alertContext = useContext(AlertContext);

    const [user, setUser] = useState('');
    const [pass, setPass] = useState('');

    const [errors, setErrors] = useState([]);

    const hasError = (key) => {
        return errors.indexOf(key);
    }

    const handleSubmit = (e) => {
        e.preventDefault();

        let errors = [];

        const patternUser = /05[0-9]{7}/
        const validUser = patternUser.test(user);
        if(!validUser) {
            errors.push("username");
        }

        const patternPass = /(?=.*\d)(?=.*[a-z]).{6,}/
        const validPass = patternPass.test(pass.toLowerCase());
        if(!validPass) {
            errors.push('password')
        }

        setErrors(errors);

        if(errors.length > 0) {
            return false;
        } else {
            axios.get(url, {
                params: {
                    username: user,
                    password: pass
                }
            })
                .then((response) => {

                    switch (response.data) {
                        case -1:
                            alertContext.setAlert('User with that number has not been found',
                                'danger');
                            break;
                        case 1:
                            alertContext.setAlert('Password incorrect!', 'danger');
                            break;
                        case 2:
                            alertContext.setAlert(
                                'Your account has been blocked! Please contact the Administrator',
                                'danger');
                            break;
                        default:
                            const cookies = new Cookies();
                            cookies.set("logged_in", response.data);

                            switch (type) {
                                case 'Login':
                                    window.location.reload();
                                    break;
                                case 'Sign-Up':
                                    alertContext.setAlert('Your account has been created! You have been logged in',
                                        'success');
                                    break;
                                default:
                                    break;
                            }
                    }
                })
        }
    }

    const onChangeUser = (e) => {
        setUser(e.target.value);
    }
    const onChangePass = (e) => {
        setPass(e.target.value)
    }

    return (
        <form>
            <div className='text-center'>
                <label htmlFor="username">Phone number</label>
                <input
                    autoComplete='off'
                    className={hasError('username') ? 'form-control is-invalid' : 'form-control'}
                    name="username"
                    value={user}
                    maxLength="10"
                    onChange={onChangeUser}
                />
                <div className={hasError('username') ? 'inline-errormsg' : 'hidden'}>
                    Please type a correct phone number.
                    10 numbers starting with 05.
                </div>
            </div>

            <div className='row-sm-auto'>
                <label htmlFor="password">Password</label>
                <input
                    autoComplete='off'
                    className={hasError('password') ? 'form-control is-invalid' : 'form-control'}
                    name='password'
                    value={pass}
                    onChange={onChangePass}
                />
                <div className={hasError('password') ? 'inline-errormsg' : 'hidden'}>
                    Please type a correct password.
                    At least 6 characters containing a letter and a number.
                </div>
            </div>
            <div className="col-lg-12  padd-top">
                <button className={buttonClass} onClick={handleSubmit}>
                    {type}
                </button>
            </div>
        </form>
    );
}

Users.propTypes = {
    type: PropTypes.string.isRequired,
    buttonClass: PropTypes.string.isRequired,
    url: PropTypes.string.isRequired
}

export default Users;