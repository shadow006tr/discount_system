import React from 'react';
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import Nav from "react-bootstrap/Nav";
import {LinkContainer} from 'react-router-bootstrap'

import icon from "../../images/icon.png"
import LogOut from "../users/LogOut";
import PropTypes from "prop-types";

const NavbarComponent = (props) => {

    const isAuth = props.isAuth;

    return (
        <Navbar
            variant="dark"
            style={{backgroundColor: 'rgba(0, 0, 0, 0.4)'}}
            fixed="top"
            className="py-1"
        >
            <Container fluid collapseOnSelect expand="lg">
                <Navbar.Brand>
                    <img
                        src={icon}
                        height="30"
                        className="d-inline-block align-top"
                        alt="React Bootstrap logo"
                    />{' '}
                    Discount System
                </Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Nav className="me-auto">

                    </Nav>
                    <Nav>
                        <LinkContainer to="/">
                        {isAuth ? <Nav.Link>Home</Nav.Link> : <Nav.Link>Login</Nav.Link>}
                        </LinkContainer>



                        {isAuth ?
                            <>
                                <LinkContainer to="/settings">
                                    <Nav.Link >Settings</Nav.Link>
                                </LinkContainer>
                                <LinkContainer to="/logout">
                                    <Nav.Link onClick={LogOut}>Log out</Nav.Link>
                                </LinkContainer>
                            </>:
                            <LinkContainer to="/sign-up">
                                <Nav.Link>Sign-up</Nav.Link>
                            </LinkContainer>
                        }
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

Navbar.propTypes = {
    isAuth: PropTypes.bool.isRequired
};

export default NavbarComponent;