import React, {useEffect, useState} from 'react';
import axios from "axios";
import Cookies from 'universal-cookie';
import Row from "react-bootstrap/Row";

import OrgButton from "../layout/OrgButton";
import {Button} from "react-bootstrap";

const Settings = () => {

    const [organizationIcons, setOrganizationIcons] = useState([]);

    let token;

    useEffect(() => {

        const cookies = new Cookies();
        token = cookies.get("token");

        let data;
        let images = [];

        axios.get("http://localhost:8989/get-all-organizations")
            .then((response) => {
                data = response.data;
                for (let i = 0; i < data.length; i++) {
                    images.push(<OrgButton url={data[i].url}
                                           organizationName={data[i].organizationName}
                                           id={data[i].organizationId}
                    />)
                }
                setOrganizationIcons(images);
            });
    }, []);

    const handleSubmit = () => {
        const cookies = new Cookies();
        token = cookies.get("token");

        axios.get("http://localhost:8989/finish-settings", {params: {token}})
            .then(() => {
                window.location.replace('/');
            });
    }

    return (
        <Row style={{marginTop: 15, justifyContent:"center"}}>
            {organizationIcons}
            <Button style={{width: "30%"}} onClick={handleSubmit} variant="outline-success">Finish</Button>
        </Row>
    );
}

export default Settings;