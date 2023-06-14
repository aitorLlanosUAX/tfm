import React from "react";
import { Container } from "react-bootstrap";
import Session from "./login/Session";
import Dashboard from "./Dashboard";
import { useTranslation } from "react-i18next";

const Home = () => {
  const { state } = React.useContext(Session);
  const { t } = useTranslation();
  let user =
    window.sessionStorage.getItem("user") == null
      ? state.user
      : sessionStorage.getItem("user");

  if (user == null)
    return (
      <Container>
        <h1 className="py-3 text-center">{t("Welcome")}</h1>
      </Container>
    );
  else {
    return <Dashboard />;
  }
};
export default Home;
