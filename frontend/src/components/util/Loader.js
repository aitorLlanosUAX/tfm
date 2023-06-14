import React from "react";
import { Row, Card, Container } from "react-bootstrap";

const Loader = (props) => {
  return (
    <Container>
      <Card className="m-5 border-0 shadow align-items-center">
        <Row>
          <Card.Body>
            <div className="loader"></div>
          </Card.Body>
        </Row>
      </Card>
    </Container>
  );
};

export default Loader;
