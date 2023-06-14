import React from "react";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faList, faTrash, faEdit } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const ProviderTableContent = (props) => {
  const providers = props.providers;
  const history = useHistory();

  const deleteProvider = (providerId) => {
    let urlSearch = new URL("http://localhost:8080/provider/delete");
    let params = {
      id: providerId,
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
        history.push("/provider/list");
      })
      .catch(console.log("Network Error"));
  };

  const checkProvider = (name) => {
    history.push("/provider/checkcreedentials/" + name);
  };

  const updateProvider = (name) => {
    history.push("/provider/update/" + name);
  };
  return providers.map((provider) => (
    <tr className="align-items-center" key={provider.id}>
      <td className="col-md-3 text-center">{provider.name}</td>
      <td className="text-center">
        <img
          alt={provider.name}
          src={`${process.env.PUBLIC_URL}/icons/${provider.name}.png`}
        />
      </td>

      <td className="col-md-2">
        <Button
          className="mx-1"
          variant="primary"
          type="submit"
          id={"checkProvider " + provider.name}
          onClick={() => checkProvider(provider.name)}
        >
          <FontAwesomeIcon icon={faList} />
        </Button>
        <Button
          className="mx-1"
          variant="primary"
          type="submit"
          id={"updateProvider " + provider.name}
          onClick={() => updateProvider(provider.name)}
        >
          <FontAwesomeIcon icon={faEdit} />
        </Button>
        <Button
          className="mx-1"
          variant="primary"
          type="submit"
          id={"deleteProvider " + provider.name}
          onClick={() => deleteProvider(provider.id)}
        >
          <FontAwesomeIcon icon={faTrash} />
        </Button>
      </td>
    </tr>
  ));
};

export default ProviderTableContent;
