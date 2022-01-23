import React, {useEffect, useState} from 'react';
import axios from "axios";
import Cookies from 'universal-cookie';
import Row from "react-bootstrap/Row";

import OrgButton from "../layout/OrgButton";
import {Button} from "react-bootstrap";

const Settings = () => {

    const [organizationIcons, setOrganizationIcons] = useState([]);
    const [token, setToken] = useState("");
    const [checked, setChecked] = useState(null);

    useEffect(() => {

        const cookies = new Cookies();
        setToken(cookies.get("token"));

        let data;
        let images = [];

        axios.get("http://localhost:8989/get-all-organizations")
            .then((response) => {
                data = response.data;
                for (let i = 0; i < data.length; i++) {
                    axios.get("http://localhost:8989/check-connection-to-organization", {
                        params: {
                            token: token,
                            idOrganization: data[i].organizationId
                        }
                    })
                        .then((response) => {
                            setChecked(response.data)
                        })
                    images.push(<OrgButton url={data[i].url}
                                           organizationName={data[i].organizationName}
                                           id={data[i].organizationId}
                                           checked={checked}
                                           token={token}
                    />)
                }
                setOrganizationIcons(images);
            });
    }, [token]);

    const handleSubmit = () => {

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