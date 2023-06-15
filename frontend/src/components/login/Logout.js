import React from "react";
import { Alert } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";

const Logout = () => {
  const navigate = useNavigate();
  navigate("/");
  const { t } = useTranslation();
  sessionStorage.removeItem("user");
  sessionStorage.removeItem("role");
  return <Alert>{t("LoggedOut")}</Alert>;
};

export default Logout;
