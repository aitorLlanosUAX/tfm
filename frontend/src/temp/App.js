import React, { useState } from "react";
import "./App.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";

import Session from "./components/login/Session";
import ProviderContext from "./components/terraformDatabase/ProviderContext";

import AdminRouter from "./components/routers/AdminRouter";
import LoggedUserRouter from "./components/routers/LoggedUserRouter";
import UnknownUserRouter from "./components/routers/UnknownUserRouter";

import i18n from "i18next";
import { initReactI18next } from "react-i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import HttpApi from "i18next-http-backend";

function App() {
  i18n
    .use(initReactI18next)
    .use(LanguageDetector)
    .use(HttpApi)
    .init({
      supportedLngs: ["en", "es"],
      fallbackLng: "en",
      detection: {
        order: [
          "cookie",
          "localStorage",
          "sessionStorage",
          "navigator",
          "htmlTag",
          "path",
          "subdomain",
        ],
        caches: ["cookie"],
      },
      backend: {
        loadPath: "/locales/{{lng}}/translation.json",
      },
      react: { useSuspense: false },
    });
  const [state, setState] = useState({ user: null, product: null });
  const [stateProvider, setStateProvider] = useState({
    processName: null,
    instanceNumber: null,
    description: null,
    provider: null,
    region: null,
    image: null,
    instance: null,
    credentials: null,
  });
  let role =
    window.sessionStorage.getItem("role") == null
      ? state.user?.role
      : sessionStorage.getItem("role");

  let user =
    window.sessionStorage.getItem("user") == null
      ? state.user
      : sessionStorage.getItem("user");

  return (
    <Session.Provider value={{ state, setState }}>
      <ProviderContext.Provider value={{ stateProvider, setStateProvider }}>
        {user == null && <UnknownUserRouter />}
        {user != null && role != null && role === "ADMIN" && <AdminRouter />}
        {user != null && role != null && role === "USER" && (
          <LoggedUserRouter />
        )}
      </ProviderContext.Provider>
    </Session.Provider>
  );
}

export default App;
