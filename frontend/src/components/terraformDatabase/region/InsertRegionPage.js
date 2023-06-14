import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Alert, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const InsertRegionPage = () => {
  const [regionName, setRegionName] = useState();
  const [zone, setZone] = useState();
  const history = useHistory();
  const { t } = useTranslation();
  const [providers, setProviders] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const [providerSelected, setProviderSelected] = useState();

  const CreateRegion = async () => {
    let url = new URL("http://localhost:8080/region/insert");
    let params = {
      name: regionName,
      zone: zone,
      provider_id:
        providerSelected === undefined
          ? document.getElementById("providerSelect").value
          : providerSelected,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.text())
      .catch(console.log("Network Error"));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await CreateRegion();
    if (result === "400") {
      document.getElementById("errorAdd").textContent = t("BLANK_FIELDS");
      document.getElementById("errorAdd").hidden = false;
      return;
    }
    history.push("/dashboard");
    history.push("/region/list");
  };

  useEffect(() => {
    fetch("http://localhost:8080/provider/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setProviders(result);
      })
      .catch(console.log("Network Error"));
  }, []);

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row>
          <div className="py-1">
            <Alert variant="danger" id="errorAdd" hidden={true}></Alert>
          </div>
          <h1 className="py-3">{t("CreateRegion")}</h1>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label>{t("Name")}: </Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter the region name"
              name="regionName"
              onChange={(e) => setRegionName(e.target.value)}
            />
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formZone">
            <Form.Label>{t("Zone")}: </Form.Label>
            <Form.Control
              type="text"
              placeholder="Enter the region zone"
              name="regionZone"
              onChange={(e) => setZone(e.target.value)}
            ></Form.Control>
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="providerSelect"
              onChange={(e) => setProviderSelected(e.target.value)}
            >
              {isLoaded &&
                providers.map((provider) => (
                  <option value={provider.id}>{provider.name}</option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Row>
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/region/list")}
              type="back"
              name="back"
            >
              {t("Back")}
            </Button>
            <Button variant="primary" type="submit" name="submit">
              {t("Create")}
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default InsertRegionPage;
