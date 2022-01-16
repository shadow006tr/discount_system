import React from 'react';
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";

import icon from "../../images/icon.png"

const NavbarComponent = () => {
    return (
        <Navbar
            variant="dark"
            style={{backgroundColor: 'rgba(0, 0, 0, 0.2)'}}
            fixed="top"
            className="py-1"
        >
            <Container fluid>
                <Navbar.Brand>
                    <img
                        src={icon}
                        height="30"
                        className="d-inline-block align-top"
                        alt="React Bootstrap logo"
                    />{' '}
                    Discount System
                </Navbar.Brand>
            </Container>
        </Navbar>
    );
}

export default NavbarComponent;