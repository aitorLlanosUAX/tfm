import React from "react";
import { Route, Switch, BrowserRouter as Router } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import { Container } from "react-bootstrap";

import ErrorPage from "../ErrorPage";
import Home from "../Home";
import TopNavBar from "../base/NavBar";
import SignUp from "../login/SignUp";
import LogIn from "../login/LogIn";
import Logout from "../login/Logout";

const UnknownUserRouter = () => {
  return (
    <Router>
      <TopNavBar />
      <Container>
        <Switch>
          <Route exact path="/" component={Home} />
          <Route path="/signup" component={SignUp} />
          <Route path="/login" component={LogIn} />
          <Route path="/logout" component={Logout} />
          <Route path="*" component={ErrorPage} />
        </Switch>
      </Container>
    </Router>
  );
};

export default UnknownUserRouter;
