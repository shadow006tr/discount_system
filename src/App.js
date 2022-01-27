import React, {useContext, useEffect, useState} from 'react';
import {
    BrowserRouter as Router, Routes, Route
} from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Cookies from 'universal-cookie';

import NavbarComponent from "./components/layout/NavbarComponent";
import Alert from './components/layout/Alert';
import Login from "./components/users/Login";
import SignUp from "./components/users/SignUp";
import Settings from "./components/pages/Settings"
import Discounts from "./components/pages/Discounts";
import NotFound from "./components/pages/NotFound";

import AlertState from './context/alert/AlertState'
import TokenState from './context/token/TokenState'
import TokenContext from "./context/token/tokenContext";

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'

const App = () => {

    const tokenContext = useContext(TokenContext);

    const [isLoggedIn, setLoggedIn] = useState(false);

    useEffect(() => {
        const cookies = new Cookies();
        if (cookies.get("token")) {
            setLoggedIn(true);
            // tokenContext.setToken(cookies.get("token"));
        }
    }, []);

    return (
        <TokenState>
            <AlertState>
                <Router>
                    <NavbarComponent isAuth = {isLoggedIn}/>
                    <Container className="app-container">
                        <Alert />
                        {isLoggedIn ?
                            <Routes>
                                <Route path="/" element={<Discounts />}/>
                                <Route path="/settings" element={<Settings />} />
                            </Routes> :
                            <Routes>
                                <Route path="/" element={<Login />} />
                                <Route path="/sign-up" element={<SignUp />} />
                            </Routes>
                        }
                        <Routes>
                            <Route element={<NotFound />} />
                        </Routes>
                    </Container>
                </Router>
            </AlertState>
        </TokenState>
    );
}

export default App;