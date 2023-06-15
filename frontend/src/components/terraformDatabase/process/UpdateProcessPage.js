import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";

const UpdateProcessPage = () => {
  const { processId } = useParams();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [process, setProcess] = useState();
  const [isLoaded, setIsLoaded] = useState(false);
  const UpdateProcess = async () => {
    let url = new URL("http://localhost:8080/process/update");
    let params = {
      id: process.id,
      name: process.name,
      description: process.description,
      active: process.active,
      status: process.status,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.json())
      .catch(console.log("Network Error"));
  };

  useEffect(() => {
    const url = new URL("http://localhost:8080/process/findById");
    url.searchParams.append("id", processId);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setProcess(result);
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, [processId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    await UpdateProcess();
    navigate("/dashboard");
  };
  return (
    isLoaded && (
      <Container>
        <Row>
          <Col>
            <h1>{t("UpdateProcess")}</h1>
          </Col>
        </Row>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label> {t("ProcessName")}: </Form.Label>
            <Form.Control type="text" value={process.name} disabled />
          </Form.Group>
          <Form.Group className="mb-md-3" controlId="formDescrption">
            <Form.Label> {t("DescriptionProcess")}: </Form.Label>
            <Form.Control
              as="textarea"
              value={process.description}
              disabled
            ></Form.Control>
          </Form.Group>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label> {t("NumberInstances")}: </Form.Label>
            <Form.Control
              type="number"
              value={process.instanceNumber}
              disabled
            />
          </Form.Group>
          {process.active ? (
            <Row>
              <Col className="col-md-2">
                <Form.Group controlId="formActive">
                  <Form.Check
                    type="checkbox"
                    label={t("Active")}
                    checked
                    onChange={(e) =>
                      setProcess({ ...process, active: e.target.checked })
                    }
                  />
                </Form.Group>
              </Col>
            </Row>
          ) : (
            <Row>
              <Col className="col-md-2">
                <Form.Group className="col-md-2" controlId="formActive">
                  <Form.Check
                    type="checkbox"
                    label={t("Active")}
                    onChange={(e) =>
                      setProcess({ ...process, active: e.target.checked })
                    }
                  />
                </Form.Group>
              </Col>
            </Row>
          )}
          <Row>
            <Col className="col-md-2 my-5 ms-auto">
              <Button
                className="mx-2"
                variant="primary"
                onClick={() => navigate("/dashboard")}
              >
                {t("Back")}
              </Button>

              <Button variant="primary" type="submit">
                {t("Update")}
              </Button>
            </Col>
          </Row>
        </Form>
      </Container>
    )
  );
};

export default UpdateProcessPage;
