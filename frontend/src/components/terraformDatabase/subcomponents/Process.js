import React from "react";
import { Badge, Button } from "react-bootstrap";
import Moment from "react-moment";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faEdit, faList } from "@fortawesome/free-solid-svg-icons";
import { useTranslation } from "react-i18next";
import Session from "../../login/Session";
import { useHistory } from "react-router-dom";

const Process = (props) => {
  const processes = props.processes;
  const { t } = useTranslation();
  const { state } = React.useContext(Session);
  const history = useHistory();

  let role =
    window.sessionStorage.getItem("role") == null
      ? state.user?.role
      : sessionStorage.getItem("role");
  const checkProcess = (id) => {
    history.push("/process/" + id);
  };
  const updateProcess = (id) => {
    history.push("/process/update/" + id);
  };

  const deleteProcess = (processId) => {
    let urlSearch = new URL("http://localhost:8080/process/delete");
    let params = {
      id: processId,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((result) => {
        history.push("/login");
        history.push("/dashboard");
      })
      .catch(console.log("Network Error"));
  };
  return processes.map((process) => (
    <tr className="align-items-center" key={process.id}>
      <td className="text-center">{process.name}</td>
      <td className="text-center">{process.description}</td>
      <td className="text-center">
        <Moment format="DD/MM/YYYY">{process.created_at}</Moment>
      </td>
      {process.status === "Running" ? (
        <td className="text-center">
          <Badge bg="success">{t("Running")}</Badge>
        </td>
      ) : (
        <td className="text-center">
          <Badge bg="warning">{t("Pending")}</Badge>
        </td>
      )}
      {role === "ADMIN" && (
        <td className="col-md-2">
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"checkProcess " + process.name}
            onClick={() => checkProcess(process.id)}
          >
            <FontAwesomeIcon icon={faList} />
          </Button>
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"updateProcess " + process.name}
            onClick={() => updateProcess(process.id)}
          >
            <FontAwesomeIcon icon={faEdit} />
          </Button>
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"deleteProcess " + process.name}
            onClick={() => deleteProcess(process.id)}
          >
            <FontAwesomeIcon icon={faTrash} />
          </Button>
        </td>
      )}
    </tr>
  ));
};

export default Process;
