import React, { useReducer } from 'react';
import PropTypes from "prop-types";
import TokenContext from "./tokenContext";
import TokenReducer from "./tokenReducer";
import { SET_TOKEN } from "../types";

const TokenState = props => {
    TokenState.propTypes = {
        children: PropTypes.any
    };
    const initialState = null;

    const [state, dispatch] = useReducer(TokenReducer, initialState);

    const setToken = token => {
        dispatch({
            type: SET_TOKEN,
            payload: token
        });
    }
    return (
        <TokenContext.Provider
            value = {{
                token: state,
                setToken
            }}
        >
            {props.children}
        </TokenContext.Provider>
    );
}

export default TokenState;