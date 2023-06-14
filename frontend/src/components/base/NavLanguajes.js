import React from "react";
import { Nav, Dropdown } from "react-bootstrap";
import { faGlobe } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import i18next from "i18next";
import { useTranslation } from "react-i18next";

const NavLanguajes = () => {
  const { t } = useTranslation();

  return (
    <Nav.Link>
      <Dropdown>
        <Dropdown.Toggle variant="outline-dark" id="dropdown-basic">
          <FontAwesomeIcon icon={faGlobe} />
        </Dropdown.Toggle>

        <Dropdown.Menu>
          <Dropdown.Item onClick={() => i18next.changeLanguage("es")}>
            <img
              className="px-1"
              alt="Spanish"
              id="Spanish"
              src={`${process.env.PUBLIC_URL}/icons/spanishFlag.png`}
            />
            {t("Spanish")}
          </Dropdown.Item>
          <Dropdown.Item onClick={() => i18next.changeLanguage("en")}>
            <img
              className="px-1"
              alt="English"
              id="English"
              src={`${process.env.PUBLIC_URL}/icons/usFlag.png`}
            />
            {t("English")}
          </Dropdown.Item>
        </Dropdown.Menu>
      </Dropdown>
    </Nav.Link>
  );
};

export default NavLanguajes;
