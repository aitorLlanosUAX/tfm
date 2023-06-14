import React, { useEffect, useState } from "react";
import { Container, Table, Row, Col, Button } from "react-bootstrap";
import RegionTableContent from "./RegionTableContent";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const ListRegionPage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [regions, setRegion] = useState([]);
  const { t } = useTranslation();
  const history = useHistory();
  useEffect(() => {
    fetch("http://localhost:8080/region/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setRegion(result);
        setIsLoaded(true);
      })
      .catch(console.log("NETWORK ERROR"));
  }, []);

  return isLoaded ? (
    <Container className="py-5">
      <Row>
        <Col>
          <h1>{t("Regions")}</h1>
        </Col>
        <Col>
          <Button
            className="pull-right rounded"
            variant="primary"
            type="submit"
            id="insertRegion"
            onClick={() => history.push("/region/insert")}
          >
            <FontAwesomeIcon className="px-1" icon={faPlus} />
            {t("AddRegion")}
          </Button>
        </Col>
      </Row>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("Zone")}</th>
            <th className="text-center">{t("Provider")}</th>
            <th className="text-center" data-priority="1"></th>
          </tr>
        </thead>
        <tbody>
          <RegionTableContent regions={regions} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ListRegionPage;
