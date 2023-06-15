import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Container, Row, Form, Button } from "react-bootstrap";
import ProviderContext from "./ProviderContext";
import { useTranslation } from "react-i18next";

const IntialProcessPage = () => {
  const [processName, setProcessName] = useState();
  const [instanceNumber, setInstanceNumber] = useState(-1);
  const [description, setDescription] = useState();
  const navigate = useNavigate();
  const { stateProvider, setStateProvider } = React.useContext(ProviderContext);
  const { t } = useTranslation();

  const handleSubmit = () => {
    setStateProvider({
      ...stateProvider,
      processName: processName,
      instanceNumber: instanceNumber,
      description: description,
    });
    sessionStorage.setItem("processName", processName);
    sessionStorage.setItem("instanceNumber", instanceNumber);
    sessionStorage.setItem("description", description);

    navigate("/providersAvailable");
  };
  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <Row>
          <h1 className="py-3">{t("CreateProcess")}</h1>
        </Row>
        <Form.Group className="mb-md-3" controlId="formName">
          <Form.Label>{t("ProcessName")}: </Form.Label>
          <Form.Control
            type="text"
            name="processName"
            placeholder="Enter the process name"
            onChange={(e) => setProcessName(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formDescrption">
          <Form.Label>{t("DescriptionProcess")}: </Form.Label>
          <Form.Control
            as="textarea"
            name="description"
            onChange={(e) => setDescription(e.target.value)}
          ></Form.Control>
        </Form.Group>
        <Form.Group controlId="formInstancesNumber">
          <Form.Label>{t("NumberInstances")}: </Form.Label>
          <Form.Control
            type="number"
            placeholder="Enter the number of instances to create"
            min="1"
            name="instanceNumber"
            onChange={(e) => setInstanceNumber(e.target.value)}
          />
        </Form.Group>
        <Row className="my-1">
          <Button
            className="col-md-1 my-5 ms-auto"
            variant="primary"
            type="submit"
            name="submit"
          >
            {t("Next")}
          </Button>
        </Row>
      </Form>
    </Container>
  );
};

export default IntialProcessPage;
