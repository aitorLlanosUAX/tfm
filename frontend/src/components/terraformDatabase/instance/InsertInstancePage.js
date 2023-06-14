import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Col, Form, Button, Alert } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const InsertInstancePage = () => {
  const [instanceName, setInstanceName] = useState();
  const [vCpus, setVCpus] = useState();
  const [memory, setMemory] = useState();
  const [cost, setCost] = useState();

  const history = useHistory();
  const { t } = useTranslation();
  const [providers, setProviders] = useState([]);
  const [regions, setRegions] = useState([]);
  const [images, setImages] = useState([]);

  const [isLoaded, setIsLoaded] = useState(false);
  const [isLoadedRegion, setIsLoadedRegion] = useState(false);
  const [isLoadedImage, setIsLoadedImage] = useState(false);

  const [imageSelected, setImageSelected] = useState();

  const createInstance = async () => {
    let url = new URL("http://localhost:8080/instance/insert");
    let params = {
      name: instanceName,
      vCpus: vCpus,
      memory: memory,
      cost: cost,
      image_id:
        imageSelected === undefined
          ? document.getElementById("imageSelect").value
          : imageSelected,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.text())
      .catch((e) => {
        console.log(e);
      })
      .catch(console.log("Network Error"));
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await createInstance();
    if (result === "406") {
      document.getElementById("errorAdd").textContent = t("BLANK_FIELDS");
      document.getElementById("errorAdd").hidden = false;
      return;
    }
    history.push("/dashboard");
    history.push("/instance/list");
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
      .catch(console.log("Network Error"));
  }, []);

  const getRegions = (id) => {
    if (id === undefined) return;
    const url = new URL("http://localhost:8080/region/fromProvider");
    url.searchParams.append("provider_id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        if (result === undefined) return;
        setIsLoadedRegion(true);
        setRegions(result);
        if (result.length === 0) {
          setImages(result);
          return;
        }
        getImages(id, result[0].id);
      })
      .catch(console.log("Network Error"));
  };
  const getImages = (provider_id, region_id) => {
    if (provider_id === undefined || region_id === undefined) return;
    const url = new URL("http://localhost:8080/image/findByRegionAndProvider");
    url.searchParams.append("provider_id", provider_id);
    url.searchParams.append("region_id", region_id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoadedImage(true);
        setImages(result);
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
          <h1 className="py-3">{t("CreateInstance")}</h1>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label>{t("Name")}: </Form.Label>
            <Form.Control
              type="text"
              name="instanceName"
              placeholder="Enter the instance name"
              onChange={(e) => setInstanceName(e.target.value)}
            />
          </Form.Group>
        </Row>
        <Row>
          <Col>
            <Form.Group className="mb-md-3" controlId="formCpus">
              <Form.Label>{t("vCpus")}: </Form.Label>
              <Form.Control
                type="number"
                name="instanceCPU"
                placeholder="Enter the number of cpus"
                min="1"
                onChange={(e) => setVCpus(e.target.value)}
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formMemory">
              <Form.Label>{t("Memory")}: </Form.Label>
              <Form.Control
                type="number"
                name="instanceMemory"
                placeholder="Enter the memory of the instance in GiB"
                min="1"
                onChange={(e) => setMemory(e.target.value)}
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Form.Group className="mb-md-3" controlId="fromCost">
            <Form.Label>{t("Cost")}: </Form.Label>
            <Form.Control
              type="number"
              name="instanceCost"
              placeholder="Enter the cost per hour in usd"
              min="0"
              step=".01"
              onChange={(e) => setCost(e.target.value)}
            />
          </Form.Group>
        </Row>

        <Row>
          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="providerSelect"
              onChange={(e) => getRegions(e.target.value)}
            >
              {isLoaded &&
                providers.map((provider) => (
                  <option value={provider.id}>{provider.name}</option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>

        <Row>
          <Form.Group className="mb-md-3" controlId="formSelectRegion">
            <Form.Select
              aria-label="Default select example"
              id="regionSelected"
              onChange={(e) => getImages(e.target.value)}
            >
              {isLoadedRegion &&
                regions.length > 0 &&
                regions.map((region) => (
                  <option value={region.id}>{region.name}</option>
                ))}
            </Form.Select>
          </Form.Group>

          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="imageSelect"
              onChange={(e) => {
                setImageSelected(e.target.value);
              }}
            >
              {isLoadedImage &&
                images.length > 0 &&
                images.map((image) => (
                  <option value={image.id}>{image.name}</option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Row className="my-1">
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/instance/list")}
              name="back"
              id="back"
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

export default InsertInstancePage;
