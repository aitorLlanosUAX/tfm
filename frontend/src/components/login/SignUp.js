import React, { useState } from "react";
import { Button, Container, Form, Row, Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import Session from "./Session";
import { useTranslation } from "react-i18next";

const SingUpFunc = async (params) => {
  let url = new URL("http://localhost:8080/user/signup");
  Object.keys(params).forEach((key) =>
    url.searchParams.append(key, params[key])
  );
  return fetch(url, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
  })
    .then((response) => response.text())
    .catch(console.log("Network Error"));
};

const SignUp = (props) => {
  const [username, setUsername] = useState("");
  const [name, setName] = useState("");
  const [surname, setSurname] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [passwordRepeated, setPasswordRepeated] = useState("");
  const { setState } = React.useContext(Session);
  const navigate = useNavigate();
  const { t } = useTranslation();

  let params = {
    username: username,
    name: name,
    surname: surname,
    email: email,
    password: password,
    passwordRepeated: passwordRepeated,
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
    const userToLog = await SingUpFunc(params);
    if (userToLog !== "success") {
      document.getElementById("errorSignUp").textContent = t(userToLog);
      document.getElementById("errorSignUp").hidden = false;
      return;
    }
    setState({ user: userToLog });

    navigate("/login");
  };

  return (
    <Container>
      <Form onSubmit={handleSubmit}>
        <div className="py-1">
          <Alert variant="danger" id="errorSignUp" hidden={true}></Alert>
        </div>
        <h1 className="py-2">{t("SignUp")}</h1>
        <Form.Group className="mb-md-3" controlId="formUsername">
          <Form.Label>{t("Username")}: </Form.Label>
          <Form.Control
            type="text"
            name="username"
            placeholder="Enter username"
            onChange={(e) => setUsername(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formName">
          <Form.Label>{t("Name")}: </Form.Label>
          <Form.Control
            type="text"
            name="name"
            placeholder="Enter your name"
            onChange={(e) => setName(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formSurname">
          <Form.Label>{t("Surname")}: </Form.Label>
          <Form.Control
            type="text"
            name="lastName"
            placeholder="Enter your surname"
            onChange={(e) => setSurname(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formEmail">
          <Form.Label>{t("Email")}: </Form.Label>
          <Form.Control
            type="email"
            name="email"
            placeholder="Enter your email"
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formPassword">
          <Form.Label>{t("Password")}: </Form.Label>
          <Form.Control
            type="password"
            name="password"
            placeholder="Enter your password"
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <Form.Group className="mb-md-3" controlId="formPassRepeated">
          <Form.Label>{t("RepeatPassword")}: </Form.Label>
          <Form.Control
            type="password"
            name="passwordConfirm"
            placeholder="Repeat your password"
            onChange={(e) => setPasswordRepeated(e.target.value)}
          />
        </Form.Group>
        <Row className="my-1">
          <Button
            className="col-md-1 my-5 ms-auto"
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

export default SignUp;
