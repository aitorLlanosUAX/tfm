import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";

const InsertProviderPage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [providers, setProviders] = useState([]);
  const history = useHistory();
  const [providerSelected, setProviderSelected] = useState();
  const { t } = useTranslation();

  const handleSubmit = async (e) => {
    e.preventDefault();
    let id =
      providerSelected === undefined
        ? document.getElementById("providerSelect").value
        : providerSelected;
    history.push("/provider/creedentials/" + id);
  };

  useEffect(() => {
    fetch("http://localhost:8080/providerCLI/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setProviders(result);
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, []);
  return isLoaded ? (
    <Container className="py-5">
      <Form onSubmit={handleSubmit}>
        <h1 className="py-2">{t("SelectProviderToCreate")}</h1>
        <Row>
          <Form.Group className="mb-md-3" controlId="formSelect">
            <Form.Select
              aria-label="Default select example"
              id="providerSelect"
              onChange={(e) => setProviderSelected(e.target.value)}
            >
              {isLoaded &&
                providers.map((provider) => (
                  <option key={provider.id} value={provider.id}>
                    {provider.name}
                  </option>
                ))}
            </Form.Select>
          </Form.Group>
        </Row>
        <Row className="my-1">
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/provider/list")}
              id="selectBackProvider"
              name="selectBackProvider"
            >
              {t("Back")}
            </Button>
            <Button variant="primary" type="submit" id="selectAddProvider">
              {t("Select")}
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  ) : (
    <Loader />
  );
};

export default InsertProviderPage;
