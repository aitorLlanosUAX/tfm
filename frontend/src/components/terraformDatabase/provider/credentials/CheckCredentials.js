import React, { useEffect, useState } from "react";
import { Container, Row, Form, Button } from "react-bootstrap";
import Loader from "../../../util/Loader";
import { useParams, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";

const CheckCreedentials = () => {
  const { name } = useParams();
  const [isLoaded, setIsLoaded] = useState(false);
  const [isVariablesLoaded, setIsVariablesLoaded] = useState(false);
  const [creedentialsValue, setCreedentialsValue] = useState();
  const [credentials, setCreedentials] = useState();
  const { t } = useTranslation();
  const history = useHistory();

  const handleSubmit = async (e) => {
    history.push("/provider/list");
  };

  useEffect(() => {
    let urlSearch = new URL(
      "http://localhost:8080/creedentials/getCredentialsByProviderName"
    );
    let params = {
      name: name,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    fetch(urlSearch, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setCreedentialsValue(Object.values(result));
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, [name]);

  useEffect(() => {
    let urlSearch = new URL(
      "http://localhost:8080/creedentials/listFromProviderName"
    );
    let params = {
      name: name,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    fetch(urlSearch, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setCreedentials(result);
        setIsVariablesLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, [name]);
  return isLoaded && isVariablesLoaded ? (
    <Container className="py-5">
      <Form onSubmit={handleSubmit}>
        <h1 className="py-2">{t("CreedentialsActual")}</h1>

        {credentials.map((creedential, index) => (
          <Row>
            <Form.Group className="mb-md-3" controlId={creedential.name}>
              <Form.Label>{creedential.name}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={creedentialsValue[index]}
                disabled
              />
            </Form.Group>
          </Row>
        ))}
        <Row className="my-1">
          <Button
            className="col-md-1 my-5 ms-auto"
            variant="primary"
            type="submit"
          >
            {t("Back")}
          </Button>
        </Row>
      </Form>
    </Container>
  ) : (
    <Loader />
  );
};

export default CheckCreedentials;
