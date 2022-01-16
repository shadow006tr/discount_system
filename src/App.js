import React, {useEffect, useState} from 'react';
import {
    BrowserRouter as Router, Routes, Route
} from 'react-router-dom';
import Container from 'react-bootstrap/Container';
import Cookies from 'universal-cookie';

import NavbarComponent from "./components/layout/NavbarComponent";
import Alert from './components/layout/Alert';
import Login from "./components/users/Login";
import SignUp from "./components/users/SignUp";
import NotFound from "./components/pages/NotFound";

import AlertState from './context/alert/AlertState'

import 'bootstrap/dist/css/bootstrap.min.css';
import './App.css'

const App = () => {
    const [isLoggedIn, setLoggedIn] = useState(false);
    const [token, setToken] = useState('');

    useEffect(() => {
        const cookies = new Cookies();
        if (cookies.get("logged_in")) {
            setLoggedIn(true);
            setToken(cookies.get("logged_in"));
        }
    }, []);

    return (
        <AlertState>
            <Router>
                <NavbarComponent />
                <Container fluid className="app-container">
                    <Alert />
                    {isLoggedIn ?
                        <Routes>
                            {/*<Route path="/" element={<Messenger token={token} />} />*/}
                            {/*<Route path="/send" element={<MessageSender />} />*/}
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
    );
}

export default App;