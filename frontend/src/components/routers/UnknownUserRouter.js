import React from "react";
import { Route, Routes, BrowserRouter as Router } from "react-router-dom";
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
        <Routes>
          <Route exact path="/" element={<Home />} />
          <Route path="/signup" element={<SignUp/>} />
          <Route path="/login" element={<LogIn/>} />
          <Route path="/logout" element={<Logout/>} />
          <Route path="*" element={<ErrorPage/>} />
        </Routes>
      </Container>
    </Router>
  );
};

export default UnknownUserRouter;
