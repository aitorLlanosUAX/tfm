import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFilePdf } from "@fortawesome/free-solid-svg-icons";

const CheckProcessPage = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { t } = useTranslation();
  const [process, setProcess] = useState();
  const [isLoaded, setIsLoaded] = useState(false);
  useEffect(() => {
    const url = new URL("http://localhost:8080/process/findById");
    url.searchParams.append("id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setProcess(result);
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  }, [id]);

  const handleSubmit = async (e) => {
    navigate("/dashboard");
  };

  return (
    isLoaded && (
      <Container>
        <Row>
          <Col className="py-2">
            <h1>{t("CheckProcess")}</h1>
          </Col>
        </Row>
        <Form>
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
                    disabled
                  />
                </Form.Group>
              </Col>
            </Row>
          ) : (
            <Row>
              <Col className="col-md-2">
                <Form.Group className="col-md-2" controlId="formActive">
                  <Form.Check type="checkbox" label={t("Active")} disabled />
                </Form.Group>
              </Col>
            </Row>
          )}
          <Row className="my-1">
            <Col className="col-md-1 my-1">
              <FontAwesomeIcon icon={faFilePdf} />
            </Col>
            <Col className="col-md-1 my-1">
              <Button variant="primary">
                <a
                  className="text-white"
                  target="_blank"
                  href={
                    "http://localhost:8080/process/downloadTemplate?process_id=" +
                    process.id
                  }
                  rel="noreferrer"
                >
                  {t("Download")}
                </a>
              </Button>
            </Col>
          </Row>

          <Row className="my-1">
            <Col className="col-md-1 my-5 ms-auto">
              <Button
                variant="primary"
                type="submit"
                onClick={() => handleSubmit()}
              >
                {t("Back")}
              </Button>
            </Col>
          </Row>
        </Form>
      </Container>
    )
  );
};

export default CheckProcessPage;
