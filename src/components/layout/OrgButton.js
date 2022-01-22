import React, {useState} from 'react';
import ToggleButton from "react-bootstrap/ToggleButton";
import Col from "react-bootstrap/Col";

const OrgButton = (props) => {

    const [checked, setChecked] = useState(false);
    const [outlineColor, setOutlineColor] = useState("black");
    const [outlineSize, setOutlineSize] = useState(1);

    const check = (e) => {
        setChecked(e.currentTarget.checked);
        if (checked) {
            setOutlineColor("black");
            setOutlineSize(1);
        } else {
            setOutlineColor("#198754");
            setOutlineSize(5);
        }
        this.blur();
    }

    const outlineType = {
        outlineStyle: "solid",
        outlineColor: outlineColor,
        outlineWidth: outlineSize,
        focusable: "false"
    };

    return (
        <Col style={
            {width: 300, height: 190, margin: 15}
        }>
            <ToggleButton
                className="orgButton shadow-none"
                id={`toggle-check-${props.id}`}
                type="checkbox"
                variant="light"
                focusable = "false"
                checked={checked}
                value={props.id}
                onChange={check}
                style={outlineType}
            >
                <img style={{width: 250}} src={props.url}  alt={props.organizationName}/>
            </ToggleButton>
        </Col>
    );
}

export default OrgButton;