import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Instance from "./subcomponents/Instance";
import { Container, Table } from "react-bootstrap";
import Loader from "../util/Loader";
import { useTranslation } from "react-i18next";

const InstancePage = () => {
  const { imageId } = useParams();
  const [instances, setInstances] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const { t } = useTranslation();

  useEffect(() => {
    const url = new URL("http://localhost:8080/instance/fromImage");
    url.searchParams.append("image_id", imageId);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setInstances(result);
      })
      .catch(console.log("Network Error"));
  }, [imageId]);
  return isLoaded ? (
    <Container className="py-5">
      <h1 className="py-2">{t("InstancesAvailable")}</h1>
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
          <Instance instances={instances} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default InstancePage;
