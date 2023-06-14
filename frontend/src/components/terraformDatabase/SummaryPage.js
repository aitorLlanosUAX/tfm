import React from "react";
import { useHistory } from "react-router-dom";
import { Container, Row, Form, Button, Col, Alert } from "react-bootstrap";
import ProviderContext from "./ProviderContext";
import { useTranslation } from "react-i18next";

const SummaryPage = () => {
  const { stateProvider } = React.useContext(ProviderContext);
  const history = useHistory();
  const { t } = useTranslation();
  const costFinal =
    parseFloat(stateProvider.instanceNumber) *
    parseFloat(stateProvider.instance.cost);
  const CreateProcess = async () => {
    let url = new URL("http://localhost:8080/process/newProcess");
    let paramProcessName =
      window.sessionStorage.getItem("processName") == null
        ? stateProvider.processName
        : sessionStorage.getItem("processName");
    let paramDescription =
      window.sessionStorage.getItem("description") == null
        ? stateProvider.description
        : sessionStorage.getItem("description");
    let paramInstanceNumber =
      window.sessionStorage.getItem("instanceNumber") == null
        ? stateProvider.instanceNumber
        : sessionStorage.getItem("instanceNumber");
    let paramProvider =
      window.sessionStorage.getItem("provider") == null
        ? stateProvider.provider.id
        : sessionStorage.getItem("provider");
    let paramRegion =
      window.sessionStorage.getItem("region") == null
        ? stateProvider.region.id
        : sessionStorage.getItem("region");
    let paramImage =
      window.sessionStorage.getItem("image") == null
        ? stateProvider.image.id
        : sessionStorage.getItem("image");
    let paramInstance =
      window.sessionStorage.getItem("instance") == null
        ? stateProvider.instance.id
        : sessionStorage.getItem("instance");
    let params = {
      processName: paramProcessName,
      description: paramDescription,
      instanceNumber: paramInstanceNumber,
      provider_id: paramProvider,
      region_id: paramRegion,
      image_id: paramImage,
      instance_id: paramInstance,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.text())
      .catch(console.log("Network Error"));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const result = await CreateProcess();
    if (result !== "Success") {
      e.preventDefault();
      document.getElementById("errorProcess").textContent = result;
      document.getElementById("errorProcess").hidden = false;
      return;
    }
    sessionStorage.removeItem("processName");
    sessionStorage.removeItem("description");
    sessionStorage.removeItem("instanceNumber");
    sessionStorage.removeItem("provider");
    sessionStorage.removeItem("region");
    sessionStorage.removeItem("image");
    sessionStorage.removeItem("instance");

    history.push("/dashboard");
  };
  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <div className="py-2">
          <Alert variant="danger" id="errorProcess" hidden={true}></Alert>
        </div>
        <h1>{t("SummaryPage")}</h1>
        <Form.Group className="mb-md-3" controlId="formName">
          <Form.Label> {t("ProcessName")}: </Form.Label>
          <Form.Control
            type="text"
            value={stateProvider.processName}
            disabled
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formDescrption">
          <Form.Label> {t("DescriptionProcess")}: </Form.Label>
          <Form.Control
            as="textarea"
            value={stateProvider.description}
            disabled
          ></Form.Control>
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formName">
          <Form.Label> {t("NumberInstances")}: </Form.Label>
          <Form.Control
            type="number"
            value={stateProvider.instanceNumber}
            disabled
          />
        </Form.Group>

        <Row>
          <Col>
            <Form.Group className="mb-md-3" controlId="formProvider">
              <Form.Label>{t("Provider")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.provider.name}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formRegion">
              <Form.Label>{t("Region")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.region.name}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formImage">
              <Form.Label>{t("Image")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.image.name}
                disabled
              />
            </Form.Group>
          </Col>
        </Row>
        <Row className="my-1">
          <Col>
            <Form.Group className="mb-md-3" controlId="formInstance">
              <Form.Label>{t("Instance")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.instance.name}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formCpus">
              <Form.Label>{t("vCpus")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.instance.vCpu}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formMemory">
              <Form.Label>{t("Memory")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.instance.memory}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formCost">
              <Form.Label>{t("Cost")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.instance.cost}
                disabled
              />
            </Form.Group>
          </Col>
        </Row>

        <Row>
          <Col>
            <Form.Group className="mb-md-3" controlId="formProvider">
              <Form.Label>{t("Provider")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.provider.name}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formRegion">
              <Form.Label>{t("Region")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.region.name}
                disabled
              />
            </Form.Group>
          </Col>
          <Col>
            <Form.Group className="mb-md-3" controlId="formImage">
              <Form.Label>{t("Image")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.image.name}
                disabled
              />
            </Form.Group>
          </Col>
        </Row>
        <Row className="my-1">
          <Col>
            <Form.Group className="mb-md-3" controlId="formCost">
              <Form.Label>{t("FinalCost")}: </Form.Label>
              <Form.Control
                type="text"
                placeholder={stateProvider.instance.cost}
                disabled
              />
            </Form.Group>
          </Col>
        </Row>

        <Row className="my-1">
          <Form.Group className="mb-md-3 ms-auto" controlId="formCost">
            <Form.Label>{t("FinalCost")}: </Form.Label>
            <Form.Control type="text" placeholder={costFinal} disabled />
          </Form.Group>
        </Row>
        <Row className="my-1">
          <Col className="col-md-1 my-5 ms-auto">
            <Button
              variant="primary"
              type="submit"
              onClick={() => history.push("/")}
            >
              {t("Cancel")}
            </Button>
          </Col>
          <Col className="col-md-1 my-5 ">
            <Button variant="primary" name="submit" id="submit" type="submit">
              {t("Create")}
            </Button>
          </Col>
        </Row>
      </Form>
    </Container>
  );
};

export default SummaryPage;
