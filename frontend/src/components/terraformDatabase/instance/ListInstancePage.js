import React, { useEffect, useState } from "react";
import { Container, Table, Row, Col, Button } from "react-bootstrap";
import InstanceTableContent from "./InstanceTableContent";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const ListInstancePage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [instances, setInstances] = useState([]);
  const { t } = useTranslation();
  const navigate = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/instance/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setInstances(result);
      })
      .catch(setIsLoaded(false))
      .catch(console.log("Network Error"));
  }, []);

  return isLoaded ? (
    <Container className="py-5">
      <Row>
        <Col>
          <h1>{t("Instances")}</h1>
        </Col>
        <Col>
          <Button
            className="pull-right rounded"
            variant="primary"
            type="submit"
            id="insertInstance"
            onClick={() => navigate("/instance/insert")}
          >
            <FontAwesomeIcon className="px-1" icon={faPlus} />
            {t("AddInstance")}
          </Button>
        </Col>
      </Row>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("vCpus")}</th>
            <th className="text-center">{t("Memory")}</th>
            <th className="text-center">{t("Cost")}</th>
            <th className="text-center" data-priority="1"></th>
          </tr>
        </thead>
        <tbody>
          <InstanceTableContent instances={instances} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ListInstancePage;
