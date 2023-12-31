import React from "react";
import { Button, Nav, Navbar, Dropdown } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import NavBarLogin from "../base/NavBarLogin";
import NavLanguajes from "./NavLanguajes";
import { useTranslation } from "react-i18next";
import Session from "../login/Session";

const TopNavBar = () => {
  const { state } = React.useContext(Session);
  const navigate = useNavigate();
  const { t } = useTranslation();
  let role =
    window.sessionStorage.getItem("role") == null
      ? state.user?.role
      : sessionStorage.getItem("role");

  return (
    <Navbar className="col-md p-0 " bg="primary">
      <Navbar.Toggle aria-controls="basic-navbar-nav" />
      <Navbar.Collapse id="navbar">
        <Nav.Link>
          <Button
            variant="outline-dark"
            className="mx-3"
            id="home"
            onClick={() => navigate("/")}
          >
            {t("Home")}
          </Button>
        </Nav.Link>
        {role != null && role === "ADMIN" && (
          <Nav.Link>
            <Button
              variant="outline-dark"
              className="mx-3"
              id="procesos"
              onClick={() => navigate("/dashboard")}
            >
              {t("Processes")}
            </Button>
          </Nav.Link>
        )}
        {role != null && role === "ADMIN" && (
          <Nav.Link>
            <Dropdown>
              <Dropdown.Toggle variant="outline-dark" id="terraform">
                {t("Terraform")}
              </Dropdown.Toggle>

              <Dropdown.Menu>
                <Dropdown.Item
                  id="providers"
                  onClick={() => navigate("/provider/list")}
                >
                  {t("Providers")}
                </Dropdown.Item>
                <Dropdown.Item
                  id="regions"
                  onClick={() => navigate("/region/list")}
                >
                  {t("Regions")}
                </Dropdown.Item>
                <Dropdown.Item
                  id="images"
                  onClick={() => navigate("/image/list")}
                >
                  {t("Images")}
                </Dropdown.Item>
                <Dropdown.Item
                  id="instances"
                  onClick={() => navigate("/instance/list")}
                >
                  {t("Instances")}
                </Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>
          </Nav.Link>
        )}
        <Nav className="ms-auto">
          <NavLanguajes />
          <NavBarLogin />
        </Nav>
      </Navbar.Collapse>
    </Navbar>
  );
};

export default TopNavBar;
