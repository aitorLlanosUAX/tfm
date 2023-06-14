import React, { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Alert, Col } from "react-bootstrap";
import Loader from "../../../util/Loader";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";

const AddCreedentials = () => {
  const { id } = useParams();
  const [isLoaded, setIsLoaded] = useState(false);
  const [creedentials, setCreedentials] = useState({});
  const history = useHistory();
  const creedentialsHashMap = new Map();
  const { t } = useTranslation();

  const handleSubmit = async (e) => {
    e.preventDefault();

    let jsonObject = {};
    jsonObject["provider_id"] = id;
    creedentialsHashMap.forEach((value, key) => {
      jsonObject[key] = value;
    });
    fetch("http://localhost:8080/creedentials/add", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(jsonObject),
    })
      .then((response) => response.text())
      .then((result) => {
        if (result === "Success") {
          history.push("/dashboard");
          history.push("/provider/list");
          return;
        }
        document.getElementById("errorAdd").textContent = t(result);
        document.getElementById("errorAdd").hidden = false;
        return;
      })
      .catch(console.log("Network Error"));
  };

  useEffect(() => {
    let urlSearch = new URL(
      "http://localhost:8080/creedentials/listFromProvider"
    );
    let params = {
      id: id,
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
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, [id]);

  const setCreednential = (e) => {
    let variableToStore = e.target.id;
    creedentialsHashMap.set(variableToStore, e.target.value);
  };
  return isLoaded ? (
    <Container className="py-5">
      <div className="py-1">
        <Alert variant="danger" id="errorAdd" hidden={true}></Alert>
      </div>
      <Form onSubmit={handleSubmit}>
        <h1 className="py-2">{t("InsertCreedentials")}</h1>
        {creedentials.map((creedential) => (
          <Row>
            <Form.Group className="mb-md-3">
              <Form.Label>{creedential.name}: </Form.Label>
              <Form.Control
                type="text"
                placeholder="Enter the creedential key"
                id={creedential.name}
                name={creedential.name}
                onChange={(e) => setCreednential(e)}
              />
            </Form.Group>
          </Row>
        ))}

        <Row className="my-1">
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/provider/list")}
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
  ) : (
    <Loader />
  );
};

export default AddCreedentials;
