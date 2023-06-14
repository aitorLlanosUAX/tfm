import React from "react";
import { Route, Switch, BrowserRouter as Router } from "react-router-dom";

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
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/signup" component={SignUp} />
          <Route path="/login" component={LogIn} />
          <Route path="/logout" component={Logout} />
          <Route path="/dashboard" component={Dashboard} />
          <Route path="/newProcess" component={IntialProcessPage} />
          <Route path="/provider/list" component={ListProviderPage} />
          <Route path="/region/list" component={ListRegionPage} />
          <Route path="/image/list" component={ListImageIsoPage} />
          <Route path="/instance/list" component={ListInstancePage} />
          <Route path="/providersAvailable" component={ProviderPage} />
          <Route path="/provider/region/:id" component={RegionPage} />
          <Route exact path="/process/:id" component={CheckProcessPage} />
          <Route
            path="/process/update/:processId"
            component={UpdateProcessPage}
          />
          <Route path="/provider/insert" component={InsertProviderPage} />
          <Route
            path="/provider/creedentials/:id"
            component={AddCreedentials}
          />
          <Route
            path="/provider/checkcreedentials/:name"
            component={CheckCredentials}
          />
          <Route path="/provider/update/:name" component={UpdateCreedentials} />
          <Route exact path="/region/insert" component={InsertRegionPage} />
          <Route path="/region/update/:regionId" component={UpdateRegionPage} />
          <Route path="/image/insert" component={InsertImagePage} />
          <Route path="/image/update/:imageId" component={UpdateImagePage} />
          <Route path="/instance/insert" component={InsertInstancePage} />
          <Route
            path="/instance/update/:instanceId"
            component={UpdateInstancePage}
          />

          <Route
            path="/provider/image/:idRegion/:idProvider"
            component={ImagePage}
          />
          <Route path="/provider/instance/:imageId" component={InstancePage} />
          <Route path="/provider/summary" component={SummaryPage} />
          <Route path="*" component={ErrorPage} />
        </Switch>
      </Container>
    </Router>
  );
};

export default AdminRouter;
