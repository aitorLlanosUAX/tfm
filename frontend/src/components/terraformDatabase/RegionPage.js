import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Region from "./subcomponents/Region";
import { Container, Table } from "react-bootstrap";
import Loader from "../util/Loader";
import { useTranslation } from "react-i18next";

const RegionPage = () => {
  const { id } = useParams();
  const [regions, setRegions] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const { t } = useTranslation();

  useEffect(() => {
    const url = new URL("http://localhost:8080/region/fromProvider");
    url.searchParams.append("provider_id", id);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setRegions(result);
      })
      .catch(console.log("Network Error"));
  }, [id]);
  return isLoaded ? (
    <Container className="py-5">
      <h1 className="py-2">{t("RegionsAvailable")}</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("Zone")}</th>
            <th className="text-center" data-priority="1"></th>
          </tr>
        </thead>
        <tbody>
          <Region regions={regions} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default RegionPage;
