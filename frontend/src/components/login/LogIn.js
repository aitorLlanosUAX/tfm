import React, { useState } from "react";
import { Button, Container, Form, Row, Alert } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import Session from "./Session";
import { useTranslation } from "react-i18next";

const LogIn = () => {
  const { setState } = React.useContext(Session);
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const history = useHistory();
  const { t } = useTranslation();

  const LogFunc = async () => {
    let url = new URL("http://localhost:8080/user/login");
    let params = {
      username: username,
      password: password,
    };
    return fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(params),
    })
      .then((response) => response.json())
      .catch((e) => {
        document.getElementById("errorLogIn").textContent =
          "User unknown, check username and password";
        document.getElementById("errorLogIn").hidden = false;
      });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const userToLog = await LogFunc();
    if (userToLog === undefined) {
      document.getElementById("errorLogIn").textContent =
        "User unknown, check username and password";
      document.getElementById("errorLogIn").hidden = false;
      return;
    }
    setState({ user: userToLog });
    sessionStorage.setItem("user", userToLog.username);
    sessionStorage.setItem("role", userToLog.role);
    history.push("/dashboard");
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <div className="py-1">
          <Alert variant="danger" id="errorLogIn" hidden={true}></Alert>
        </div>
        <h1 className="py-2"> {t("Login")}</h1>
        <Form.Group className="mb-3" controlId="formUsername">
          <Form.Label> {t("Username")}: </Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter username"
            name="username"
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-3" controlId="formPassword">
          <Form.Label> {t("Password")}: </Form.Label>
          <Form.Control
            type="password"
            name="password"
            placeholder="Enter your password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <Row>
          <Button
            className="col-md-1 ms-auto"
            variant="primary"
            name="submit"
            type="submit"
          >
            {t("Submit")}
          </Button>
        </Row>
      </Form>
    </Container>
  );
};
export default LogIn;
