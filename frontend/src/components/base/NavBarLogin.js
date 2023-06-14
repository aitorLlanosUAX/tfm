import React from "react";
import Session from "../login/Session";
import { useHistory } from "react-router-dom";
import { Button, Nav } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const NavBar = () => {
  const { state, setState } = React.useContext(Session);
  const history = useHistory();
  const { t } = useTranslation();
  let user =
    window.sessionStorage.getItem("user") == null
      ? state.user
      : sessionStorage.getItem("user");

  if (user == null)
    return (
      <>
        <Nav.Link>
          <Button
            variant="outline-dark"
            id="signup"
            onClick={() => history.push("/signup")}
          >
            {t("SignUp")}
          </Button>
        </Nav.Link>
        <Nav.Link>
          <Button
            variant="outline-dark"
            className="mx-3"
            id="login"
            onClick={() => history.push("/login")}
          >
            {t("Login")}
          </Button>
        </Nav.Link>
      </>
    );

  return (
    <>
      <Nav.Link>
        <Button
          className="mx-3"
          variant="outline-dark"
          id="logout"
          onClick={(e) => {
            e.preventDefault();
            setState({ user: null });
            history.push("/logout");
          }}
        >
          {t("Logout")}
        </Button>
      </Nav.Link>
    </>
  );
};

export default NavBar;
