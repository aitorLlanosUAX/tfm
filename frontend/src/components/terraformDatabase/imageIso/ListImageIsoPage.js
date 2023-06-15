import React, { useEffect, useState } from "react";
import { Container, Table, Row, Col, Button } from "react-bootstrap";
import ImageIsoTableContent from "./ImageIsoTableContent";
import Loader from "../../util/Loader";
import { useTranslation } from "react-i18next";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const ListInstancePage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [images, setImages] = useState([]);
  const { t } = useTranslation();
  const navigate = useNavigate();
  useEffect(() => {
    fetch("http://localhost:8080/image/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setImages(result);
      })
      .catch(console.log("NETWORK ERROR"));
  }, []);

  return isLoaded ? (
    <Container className="py-5">
      <Row>
        <Col>
          <h1>{t("Images")}</h1>
        </Col>
        <Col>
          <Button
            className="pull-right rounded"
            variant="primary"
            type="submit"
            id="insertImage"
            onClick={() => navigate("/image/insert")}
          >
            <FontAwesomeIcon className="px-1" icon={faPlus} />
            {t("AddImage")}
          </Button>
        </Col>
      </Row>

      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("OperatingSystem")}</th>
            <th className="text-center" data-priority="1">
              {t("Provider")}
            </th>
            <th className="text-center" data-priority="1">
              {t("Region")}
            </th>
          </tr>
        </thead>
        <tbody>
          <ImageIsoTableContent images={images} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ListInstancePage;
