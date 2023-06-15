import React, { useEffect, useState } from "react";
import { Container, Table, Row, Col, Button } from "react-bootstrap";
import ProviderTableContent from "./ProviderTableContent";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const ListProviderPage = () => {
  const [providers, setProviders] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const { t } = useTranslation();
  const navigate = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/provider/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setProviders(result);
      })
      .catch(console.log("Network Error"));
  }, []);

  return isLoaded ? (
    <Container className="py-5">
      <Row>
        <Col>
          <h1>{t("Providers")}</h1>
        </Col>
        <Col>
          <Button
            className="pull-right rounded"
            variant="primary"
            type="submit"
            id="insertProvider"
            onClick={() => navigate("/provider/insert")}
          >
            <FontAwesomeIcon className="px-1" icon={faPlus} />
            {t("AddProvider")}
          </Button>
        </Col>
      </Row>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center"></th>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center" data-priority="1"></th>
          </tr>
        </thead>
        <tbody>
          <ProviderTableContent providers={providers} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ListProviderPage;
