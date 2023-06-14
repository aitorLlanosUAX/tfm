import React from "react";
import { useTranslation } from "react-i18next";

const Footer = () => {
  const { t } = useTranslation();

  return (
    <footer className="p-1 col-md bg-primary text-white text-center fixed-bottom">
      <div>
        {t("DesignedBy")}:
        <a
          className="px-2 text-black"
          href="mailto:aitor-jose.llanos-irazola-fernandez-external@capgemini.com"
        >
          Aitor Llanos
        </a>
      </div>
    </footer>
  );
};

export default Footer;
