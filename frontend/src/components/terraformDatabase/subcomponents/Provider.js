import React from "react";
import { useNavigate } from "react-router-dom";
import ProviderContext from "../ProviderContext";
import { Row, Col, Card, Button } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const Provider = (props) => {
  const navigate = useNavigate();
  const provider = props.prov;

  const { stateProvider, setStateProvider } = React.useContext(ProviderContext);
  const { t } = useTranslation();

  const submit = () => {
    setStateProvider({ ...stateProvider, provider: provider });
    sessionStorage.setItem("provider", provider.id);
    navigate(`/provider/region/${provider.id}`);
  };

  return (
    <Col>
      <Card className="m-5 border-0 shadow align-items-center">
        <Row>
          <Col className="py-4">
            <Card.Img
              src={`${process.env.PUBLIC_URL}/icons/${provider.name}.png`}
            />
          </Col>
        </Row>
        <Row>
          <Card.Body>
            <Card.Title>{provider.name}</Card.Title>
          </Card.Body>
        </Row>
        <Row className="col-md-2 py-2">
          <Button
            variant="primary"
            id="selectProvider"
            onClick={() => submit()}
          >
            {t("Select")}
          </Button>
        </Row>
      </Card>
    </Col>
  );
};

export default Provider;
