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
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/signup" component={SignUp} />
          <Route path="/login" component={LogIn} />
          <Route path="/logout" component={Logout} />
          <Route path="/dashboard" component={Dashboard} />
          <Route path="/newProcess" component={IntialProcessPage} />
          <Route path="/providersAvailable" component={ProviderPage} />
          <Route path="/provider/region/:id" component={RegionPage} />

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

export default LoggedUserRouter;
