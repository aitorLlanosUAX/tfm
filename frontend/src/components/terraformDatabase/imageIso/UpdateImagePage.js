import React, { useState, useEffect } from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Col } from "react-bootstrap";
import { useTranslation } from "react-i18next";
import { useParams } from "react-router-dom";
import Loader from "../../util/Loader";
const UpdateImagePage = () => {
  const [imageName, setImageName] = useState();
  const [operatingSystem, setOperatingSystem] = useState();

  const { imageId } = useParams();

  const history = useHistory();
  const { t } = useTranslation();
  const [image, setImage] = useState({});
  const [provider, setProvider] = useState({});
  const [region, setRegion] = useState({});

  const [isLoaded, setIsLoaded] = useState(false);
  const [isLoadedRegion, setIsLoadedRegion] = useState(false);

  const UpdateImage = async () => {
    let url = new URL("http://localhost:8080/image/update");
    let params = {
      id: image.id,
      name: imageName,
      operatingSystem: operatingSystem,
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
    await UpdateImage();
    history.push("/image/list");
  };

  useEffect(() => {
    const url = new URL("http://localhost:8080/image/findById");
    url.searchParams.append("id", imageId);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        getProvider(result.provider_id);
        getRegions(result.region_id);
        setImage(result);
      })
      .catch(console.log("Network Error"));
  }, [imageId]);

  const getRegions = async (id) => {
    const url = new URL("http://localhost:8080/region/findById");
    url.searchParams.append("id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setRegion(result);
        setIsLoadedRegion(true);
      })
      .catch(console.log("Network Error"));
  };
  const getProvider = (id) => {
    const url = new URL("http://localhost:8080/provider/findById");
    url.searchParams.append("id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setProvider(result);
        setIsLoaded(true);
      })
      .catch(console.log("Network Error"));
  };

  return isLoaded && isLoadedRegion ? (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row>
          <h1 className="py-3">{t("UpdateImage")}</h1>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formName">
            <Form.Label>{t("Name")}: </Form.Label>
            <Form.Control
              type="text"
              name="imageName"
              placeholder={image.name}
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
              placeholder={image.operatingSystem}
              onChange={(e) => setOperatingSystem(e.target.value)}
            />
          </Form.Group>
        </Row>
        <Row>
          <Form.Group className="mb-md-3" controlId="formProvider">
            <Form.Control placeholder={provider.name} disabled></Form.Control>
          </Form.Group>
        </Row>

        <Row>
          <Form.Group className="mb-md-3" controlId="formRegion">
            <Form.Control placeholder={region.name} disabled></Form.Control>
          </Form.Group>
        </Row>

        <Row>
          <Col className="col-md-2 my-5 ms-auto">
            <Button
              className="mx-2"
              variant="primary"
              onClick={() => history.push("/image/list")}
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

export default UpdateImagePage;
