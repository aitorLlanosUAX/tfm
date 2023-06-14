import React, { useEffect, useState } from "react";
import { Container, Table, Row, Col, Button } from "react-bootstrap";
import Process from "../subcomponents/Process";
import Loader from "../../util/Loader";
import { useHistory } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import Session from "../../login/Session";

const ListProcessPage = () => {
  const [isLoaded, setIsLoaded] = useState(false);
  const [processes, setProcesses] = useState([]);
  const history = useHistory();
  const { t } = useTranslation();
  const { state } = React.useContext(Session);

  let role =
    window.sessionStorage.getItem("role") == null
      ? state.user?.role
      : sessionStorage.getItem("role");

  const search = (value) => {
    let urlSearch = new URL("http://localhost:8080/process/search");
    let params = {
      partialText: value,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((p) => {
        setProcesses([]);
        p.map((process) =>
          setProcesses((processes) => {
            let id = process.id;
            let description = process.description;
            let name = process.name;
            let active = process.active;
            let creationDate = process.creationDate;

            return [
              ...processes,
              {
                id,
                name,
                description,
                active,
                creationDate,
              },
            ];
          })
        );
      })
      .catch(console.log("Network Error"));
  };
  useEffect(() => {
    fetch("http://localhost:8080/process/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        setProcesses(result);
      })
      .catch(console.log("Network Error"));
  }, []);
  return isLoaded ? (
    <Container className="py-5">
      <Row>
        <Col>
          <h1>{t("Processes")}</h1>
        </Col>
        <Col>
          <Button
            className="pull-right rounded"
            variant="primary"
            type="submit"
            id="newProcess"
            onClick={() => history.push("/newProcess")}
          >
            <FontAwesomeIcon className="px-1" icon={faPlus} />
            {t("AddProcess")}
          </Button>
        </Col>
      </Row>

      <Row>
        <Col className="col-md-2 my-2 mx-4 ms-auto">
          <input
            type="text"
            name="searchText"
            placeholder="Process name"
            onChange={(e) => search(e.target.value)}
          />
        </Col>
      </Row>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th className="text-center">{t("Name")}</th>
            <th className="text-center">{t("Description")}</th>
            <th className="text-center">{t("CreationDate")}</th>
            <th className="text-center">{t("Status")}</th>
            {role === "ADMIN" && (
              <th className="text-center" data-priority="1"></th>
            )}
          </tr>
        </thead>
        <tbody>
          <Process processes={processes} />
        </tbody>
      </Table>
    </Container>
  ) : (
    <Loader />
  );
};

export default ListProcessPage;
