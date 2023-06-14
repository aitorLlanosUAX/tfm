import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import { useParams } from "react-router-dom";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";

const UpdateRegionPage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [isProviderLoaded, setIsProviderLoaded] = useState(false);

  const { regionId } = useParams();
  const [region, setRegion] = useState({});
  const [provider, setProvider] = useState({});

  const history = useHistory();
  const { t } = useTranslation();

  const UpdateRegion = async () => {
    let url = new URL("http://localhost:8080/region/update");
    let params = {
      id: region.id,
      name: region.name,
      zone: region.zone,
      provider_id: region.provider_id,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.json())
      .catch((e) => {
        console.log(e);
      })
      .catch(console.log("Network Error"));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await UpdateRegion();
    history.push("/dashboard");
    history.push("/region/list");
  };

  useEffect(() => {
    const url = new URL("http://localhost:8080/region/findById");
    url.searchParams.append("id", regionId);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setRegion(result);
        getProvider(result.provider_id);
      })
      .catch(console.log("Network Error"));
  }, [regionId]);
  const getProvider = async (id) => {
    const url = new URL("http://localhost:8080/provider/findById");
    url.searchParams.append("id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setProvider(result);
        setIsProviderLoaded(true);
      })
      .catch(console.log("Network Error"));
  };
  return isLoaded && isProviderLoaded ? (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row>
          <h1 className="py-3">{t("UpdateRegion")}</h1>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label>{t("Name")}: </Form.Label>
            <Form.Control
              type="text"
              name="regionName"
              placeholder={region.name}
              onChange={(e) => setRegion({ ...region, name: e.target.value })}
            />
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formZone">
            <Form.Label>{t("Zone")}: </Form.Label>
            <Form.Control
              type="text"
              name="regionZone"
              placeholder={region.zone}
              onChange={(e) => setRegion({ ...region, zone: e.target.value })}
            ></Form.Control>
          </Form.Group>
        </Row>
        <Row>
          <Form.Group controlId="formProvider">
            <Form.Label>{t("Provider")}: </Form.Label>
            <Form.Control placeholder={provider.name} disabled />
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
              {t("Update")}
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  ) : (
    <Loader />
  );
};

export default UpdateRegionPage;
