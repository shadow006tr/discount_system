import React, {useEffect, useState} from 'react';
import {Button, Form, FormControl} from "react-bootstrap";
import Cookies from 'universal-cookie';
import axios from "axios";

const Discounts = () => {

    const [discounts, setDiscounts] = useState([]);

    useEffect(() => {
        const cookies = new Cookies();
        axios.get("http://localhost:8989/get-all-sales", {
            params: {
                token: cookies.get("token")
            }
        })
            .then((response) => {
                setDiscounts(response.data)
            })
    }, [])

    return (
        <div>
            <Form className="d-flex mt-5">
                <FormControl
                    type="search"
                    placeholder="Search"
                    className="me-2"
                    aria-label="Search"
                />
                <Button variant="outline-success">Search</Button>
            </Form>
            {
                discounts.map(discount => {
                    return (
                        <div style={{borderBottom: "1px solid black", padding: "10px", width: "300px"}}>
                            <i>
                                {discount.title}
                            </i>
                            <p>
                                {discount.content}
                            </p>
                        </div>
                    )
                })
            };
        </div>
    );
}

export default Discounts;