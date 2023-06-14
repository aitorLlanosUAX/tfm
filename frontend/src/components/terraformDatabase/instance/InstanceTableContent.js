import React from "react";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faEdit } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const InstanceTableContent = (props) => {
  const instances = props.instances;
  const history = useHistory();

  const deleteImage = (instanceId) => {
    let urlSearch = new URL("http://localhost:8080/instance/delete");
    let params = {
      id: instanceId,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((result) => {
        history.push("/dashboard");
        history.push("/instance/list");
      })
      .catch(console.log("Network Error"));
  };
  return instances.map((instance) => (
    <tr className="align-items-center" key={instance.id}>
      <td className="text-center">{instance.name}</td>
      <td className="text-center">{instance.vCpu}</td>
      <td className="text-center">{instance.memory}</td>
      <td className="text-center">{instance.cost}</td>

      <td className="col-md-2">
        <Button
          className="mx-1"
          variant="primary"
          type="submit"
          id={"updateInstance " + instance.name}
          onClick={() => history.push("/instance/update/" + instance.id)}
        >
          <FontAwesomeIcon icon={faEdit} />
        </Button>
        <Button
          className="mx-1"
          variant="primary"
          type="submit"
          id={"deleteInstance " + instance.name}
          onClick={() => deleteImage(instance.id)}
        >
          <FontAwesomeIcon icon={faTrash} />
        </Button>
      </td>
    </tr>
  ));
};

export default InstanceTableContent;
