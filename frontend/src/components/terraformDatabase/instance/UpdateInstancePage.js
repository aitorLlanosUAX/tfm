import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import { useParams } from "react-router-dom";
import { useTranslation } from "react-i18next";

const UpdateInstancePage = () => {
  const { instanceId } = useParams();
  const [instance, setInstance] = useState();
  const [image, setImage] = useState();

  const [isLoaded, setIsLoaded] = useState(false);
  const [isLoadedImage, setIsLoadedImage] = useState(false);

  const navigate = useNavigate();
  const { t } = useTranslation();

  const UpdateInstance = async () => {
    let url = new URL("http://localhost:8080/instance/update");
    let params = {
      id: instance.id,
      name: instance.name,
      vCpu: instance.vCpu,
      memory: instance.memory,
      cost: instance.cost,
      image_id: instance.image_id,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.json())
      .catch(console.log("Network Error"));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    await UpdateInstance();
    navigate("/dashboard");
    navigate("/instance/list");
  };

  useEffect(() => {
    const url = new URL("http://localhost:8080/instance/findById");
    url.searchParams.append("id", instanceId);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setInstance(result);
        setIsLoaded(true);
        getImage(result.imagen_id);
      })
      .catch(console.log("Network Error"));
  }, [instanceId]);

  const getImage = async (id) => {
    const url = new URL("http://localhost:8080/image/findById");
    url.searchParams.append("id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setImage(result);
        setIsLoadedImage(true);
      })
      .catch(console.log("Network Error"));
  };
  return (
    isLoaded &&
    isLoadedImage && (
      <Container>
        <Form onSubmit={handleSubmit}>
          <Row>
            <h1 className="py-3">{t("UpdateInstance")}</h1>
          </Row>
          <Row>
            <Form.Group className="mb-md-3" controlId="formName">
              <Form.Label>{t("Name")}: </Form.Label>
              <Form.Control
                type="text"
                name="instanceName"
                placeholder={instance.name}
                onChange={(e) =>
                  setInstance({ ...instance, name: e.target.value })
                }
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
                  placeholder={instance.vCpu}
                  min="1"
                  onChange={(e) =>
                    setInstance({ ...instance, vCpu: e.target.value })
                  }
                />
              </Form.Group>
            </Col>
            <Col>
              <Form.Group className="mb-md-3" controlId="formMemory">
                <Form.Label>{t("Memory")}: </Form.Label>
                <Form.Control
                  type="number"
                  name="instanceMemory"
                  placeholder={instance.memory}
                  min="1"
                  onChange={(e) =>
                    setInstance({ ...instance, memory: e.target.value })
                  }
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
                placeholder={instance.cost}
                min="0"
                step=".01"
                onChange={(e) =>
                  setInstance({ ...instance, cost: e.target.value })
                }
              />
            </Form.Group>
          </Row>

          <Row>
            <Form.Group controlId="formImageName">
              <Form.Label>{t("Image")}: </Form.Label>
              <Form.Control placeholder={image.name} disabled />
            </Form.Group>
          </Row>
          <Row>
            <Col className="col-md-2 my-5 ms-auto">
              <Button
                className="mx-2"
                variant="primary"
                onClick={() => navigate("/instance/list")}
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
    )
  );
};

export default UpdateInstancePage;
