import React from "react";
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";

import ErrorPage from "../ErrorPage";
import Home from "../Home";
import TopNavBar from "../base/NavBar";
import SignUp from "../login/SignUp";
import LogIn from "../login/LogIn";
import Logout from "../login/Logout";

import Dashboard from "../Dashboard";
import IntialProcessPage from "../terraformDatabase/InitialProcessPage";
import RegionPage from "../terraformDatabase/RegionPage";
import ProviderPage from "../terraformDatabase/ProviderPage";
import InstancePage from "../terraformDatabase/InstancePage";
import ImagePage from "../terraformDatabase/ImagePage";
import SummaryPage from "../terraformDatabase/SummaryPage";
import { Container } from "react-bootstrap";

const LoggedUserRouter = () => {
  return (
    <Router>
      <TopNavBar />
      <Container>
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<LogIn />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/newProcess" element={<IntialProcessPage />} />
          <Route path="/providersAvailable" element={<ProviderPage />} />
          <Route path="/provider/region/:id" element={<RegionPage />} />
          <Route
            path="/provider/image/:idRegion/:idProvider"
            element={<ImagePage />}
          />
          <Route path="/provider/instance/:imageId" element={<InstancePage />} />
          <Route path="/provider/summary" element={<SummaryPage />} />
          <Route path="*" element={<ErrorPage />} />
        </Routes>
      </Container>
    </Router>
  );
};

export default LoggedUserRouter;
