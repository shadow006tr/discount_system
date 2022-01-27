import React, { useReducer } from 'react';
import PropTypes from "prop-types";
import AlertContext from './alertContext';
import AlertReducer from './alertReducer';
import { SET_ALERT, REMOVE_ALERT } from '../types';

const AlertState = props => {
    AlertState.propTypes = {
        children: PropTypes.any
    };
    const initialState = null;

    const [state, dispatch] = useReducer(AlertReducer, initialState);

    // Set Alert
    const setAlert = (msg, type) => {
        dispatch({
            type: SET_ALERT,
            payload: { msg, type }
        });

        setTimeout(() => dispatch({ type: REMOVE_ALERT }), 5000);
    };

    return (
        <AlertContext.Provider
            value={{
                alert: state,
                setAlert
            }}
        >
            {props.children}
        </AlertContext.Provider>
    );
};

export default AlertState;