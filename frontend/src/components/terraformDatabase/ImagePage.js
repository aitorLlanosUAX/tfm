import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { Container, Table } from "react-bootstrap";
import Loader from "../util/Loader";
import Image from "./subcomponents/Image";
import { useTranslation } from "react-i18next";

const ImagePage = () => {
  const { idRegion, idProvider } = useParams();
  const [images, setImages] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  const { t } = useTranslation();

  useEffect(() => {
    const url = new URL("http://localhost:8080/image/findByRegionAndProvider");
    url.searchParams.append("region_id", idRegion);
    url.searchParams.append("provider_id", idProvider);
    fetch(url, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setImages(result);
      })
      .catch(console.log("Network Error"));
  }, [idRegion, idProvider]);
  return isLoaded ? (
    <Container className="py-5">
      <h1 className="py-2">{t("ImagesAvailable")}</h1>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("OperatingSystem")}</th>
            <th className="text-center" data-priority="1"></th>
          </tr>
        </thead>
        <tbody>
          <Image images={images} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ImagePage;
