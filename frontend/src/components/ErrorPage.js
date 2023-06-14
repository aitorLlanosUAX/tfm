import React from "react";
import { Card, Row } from "react-bootstrap";
import { useTranslation } from "react-i18next";

const ErrorPage = (props) => {
  const { t } = useTranslation();

  return (
    <Card className="m-5 border-0 shadow align-items-center">
      <Row>
        <Card.Img src={`${process.env.PUBLIC_URL}/icons/404notfound.png`} />
      </Row>
      <Row>
        <Card.Body>
          <Card.Title>{t("PageNotFound")}</Card.Title>
        </Card.Body>
      </Row>
    </Card>
  );
};

export default ErrorPage;
