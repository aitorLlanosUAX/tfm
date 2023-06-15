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

import RegionPage from "../terraformDatabase/RegionPage";
import ProviderPage from "../terraformDatabase/ProviderPage";
import InstancePage from "../terraformDatabase/InstancePage";
import ImagePage from "../terraformDatabase/ImagePage";

import Dashboard from "../Dashboard";
import IntialProcessPage from "../terraformDatabase/InitialProcessPage";
import SummaryPage from "../terraformDatabase/SummaryPage";
import ListInstancePage from "../terraformDatabase/instance/ListInstancePage";
import ListProviderPage from "../terraformDatabase/provider/ListProviderPage";
import ListImageIsoPage from "../terraformDatabase/imageIso/ListImageIsoPage";
import InsertProviderPage from "../terraformDatabase/provider/InsertProviderPage";
import InsertRegionPage from "../terraformDatabase/region/InsertRegionPage";
import ListRegionPage from "../terraformDatabase/region/ListRegionPage";
import UpdateRegionPage from "../terraformDatabase/region/UpateRegionPage";
import InsertImagePage from "../terraformDatabase/imageIso/InsertImagePage";
import UpdateImagePage from "../terraformDatabase/imageIso/UpdateImagePage";
import InsertInstancePage from "../terraformDatabase/instance/InsertInstancePage";
import AddCreedentials from "../terraformDatabase/provider/credentials/AddCreedentials";
import CheckCredentials from "../terraformDatabase/provider/credentials/CheckCredentials";
import CheckProcessPage from "../terraformDatabase/process/CheckProcessPage";
import UpdateProcessPage from "../terraformDatabase/process/UpdateProcessPage";
import { Container } from "react-bootstrap";
import UpdateCreedentials from "../terraformDatabase/provider/credentials/UpdateCredentials";
import UpdateInstancePage from "../terraformDatabase/instance/UpdateInstancePage";

const AdminRouter = () => {
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
          <Route path="/provider/list" element={<ListProviderPage />} />
          <Route path="/region/list" element={<ListRegionPage />} />
          <Route path="/image/list" element={<ListImageIsoPage />} />
          <Route path="/instance/list" element={<ListInstancePage />} />
          <Route path="/providersAvailable" element={<ProviderPage />} />
          <Route path="/provider/region/:id" element={<RegionPage />} />
          <Route exact path="/process/:id" element={<CheckProcessPage />} />
          <Route
            path="/process/update/:processId"
            element={<UpdateProcessPage />}
          />
          <Route path="/provider/insert" element={<InsertProviderPage />} />
          <Route
            path="/provider/creedentials/:id"
            element={<AddCreedentials />}
          />
          <Route
            path="/provider/checkcreedentials/:name"
            element={<CheckCredentials />}
          />
          <Route
            path="/provider/update/:name"
            element={<UpdateCreedentials />}
          />
          <Route exact path="/region/insert" element={<InsertRegionPage />} />
          <Route path="/region/update/:regionId" element={<UpdateRegionPage />} />
          <Route path="/image/insert" element={<InsertImagePage />} />
          <Route path="/image/update/:imageId" element={<UpdateImagePage />} />
          <Route path="/instance/insert" element={<InsertInstancePage />} />
          <Route
            path="/instance/update/:instanceId"
            element={<UpdateInstancePage />}
          />
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

export default AdminRouter;
