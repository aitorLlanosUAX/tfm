import React, { useEffect, useState } from "react";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import Loader from "../../../util/Loader";
import { useParams, useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";

const UpdateCreedentials = () => {
  const { name } = useParams();
  const [isLoaded, setIsLoaded] = useState(false);
  const [isVariablesLoaded, setIsVariablesLoaded] = useState(false);
  const [creedentialsValue, setCreedentialsValue] = useState();
  const [credentials, setCreedentials] = useState();
  const { t } = useTranslation();
  const creedentialsHashMap = new Map();
  const history = useHistory();

  const handleSubmit = async (e) => {
    e.preventDefault();

    let jsonObject = {};
    jsonObject["name"] = name;
    creedentialsHashMap.forEach((value, key) => {
      jsonObject[key] = value;
    });

    fetch("http://localhost:8080/creedentials/update", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(jsonObject),
    })
      .then((response) => response.json())
      .catch(console.log("Network Error"));
    history.push("/dashboard");
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

  const setCreednential = (e) => {
    let variableToStore = e.target.id;
    creedentialsHashMap.set(variableToStore, e.target.value);
  };

  return isLoaded && isVariablesLoaded ? (
    <Container className="py-5">
      <Form onSubmit={handleSubmit}>
        <h1 className="py-2">{t("UpdateCreedentials")}</h1>

        {credentials.map((creedential, index) => (
          <Row>
            <Form.Group className="mb-md-3" controlId={creedential.name}>
              <Form.Label>{creedential.name}: </Form.Label>
              <Form.Control
                type="text"
                name={creedential.name}
                placeholder={creedentialsValue[index]}
                onChange={(e) => setCreednential(e)}
              />
            </Form.Group>
          </Row>
        ))}
        <Row>
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/provider/list")}
              name="back"
              id="back"
            >
              {t("Back")}
            </Button>
            <Button variant="primary" type="submit" name="submit" id="submit">
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

export default UpdateCreedentials;
