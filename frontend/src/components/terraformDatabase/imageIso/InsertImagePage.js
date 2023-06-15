import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Form, Button, Alert, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const InsertImagePage = () => {
  const [imageName, setImageName] = useState();
  const [operatingSystem, setOperatingSystem] = useState();

  const navigate = useNavigate();
  const { t } = useTranslation();
  const [providers, setProviders] = useState([]);
  const [regions, setRegions] = useState([]);

  const [isLoaded, setIsLoaded] = useState(false);
  const [isLoadedRegion, setIsLoadedRegion] = useState(false);

  const [providerSelected, setProviderSelected] = useState();
  const [regionSelected, setRegionSelected] = useState();

  const CreateImage = async () => {
    let url = new URL("http://localhost:8080/image/insert");
    console.log(regionSelected)
    let params = {
      name: imageName,
      operatingSystem: operatingSystem,
      region_id:
        regionSelected === undefined
          ? document.getElementById("regionSelected").value
          : regionSelected,
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
    const result = await CreateImage();
    if (result === "400") {
      document.getElementById("errorAdd").textContent = t("BLANK_FIELDS");
      document.getElementById("errorAdd").hidden = false;
      return;
    }
    navigate("/image/list");
  };

  useEffect(() => {
    fetch("http://localhost:8080/provider/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setProviders(result);
        if (result.length === 0) {
          setRegions(result);
          return;
        }
        getRegions(result[0].id);
      })
      .catch();
  }, []);

  const getRegions = (id) => {
    const url = new URL("http://localhost:8080/region/fromProvider");
    url.searchParams.append("provider_id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoadedRegion(true);
        setRegions(result);
      })
      .catch(console.log("Network Error"));
  };
  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row>
          <div className="py-1">
            <Alert variant="danger" id="errorAdd" hidden={true}></Alert>
          </div>
          <h1 className="py-3">{t("CreateImage")}</h1>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label>{t("Name")}: </Form.Label>
            <Form.Control
              type="text"
              name="imageName"
              placeholder="Enter the image name"
              onChange={(e) => setImageName(e.target.value)}
            />
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formOperatingSystem">
            <Form.Label>{t("OperatingSystem")}: </Form.Label>
            <Form.Control
              type="text"
              name="imageOpSystem"
              placeholder="Enter the operating system"
              onChange={(e) => setOperatingSystem(e.target.value)}
            />
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="providerSelect"
              onChange={(e) => {
                setProviderSelected(e.target.value);
                getRegions(e.target.value);
              }}
            >
              {isLoaded &&
                providers.map((provider) => (
                  <option value={provider.id}>{provider.name}</option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="regionSelected"
              onChange={(e) => setRegionSelected(e.target.value)}
            >
              {isLoadedRegion &&
                regions.length > 0 &&
                regions.map((region) => (
                  <option value={region.id}>{region.name}</option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Row className="my-1">
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => navigate("/image/list")}
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

export default InsertImagePage;
