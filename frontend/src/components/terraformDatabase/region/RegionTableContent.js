import React, { useEffect, useState } from "react";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faEdit } from "@fortawesome/free-solid-svg-icons";
import { useHistory } from "react-router-dom";

const RegionTableContent = (props) => {
  const regions = props.regions;
  const [isLoaded, setIsLoaded] = useState(false);
  const history = useHistory();

  const deleteRegion = (regionId) => {
    let urlSearch = new URL("http://localhost:8080/region/delete");
    let params = {
      id: regionId,
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
        history.push("/region/list");
      })
      .catch(console.log("Network Error"));
  };

  const searchByProviderId = (region) => {
    let urlSearch = new URL("http://localhost:8080/provider/findById");
    let params = {
      id: region.provider_id,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        region.provider = result.name;
      })
      .catch(console.log("Network Error"));
  };

  useEffect(() => {
    regions.forEach((region) => searchByProviderId(region));
  }, [regions]);

  return regions.map((region) =>
    isLoaded ? (
      <tr className="align-items-center" key={region.id}>
        <td className="text-center">{region.name}</td>
        <td className="text-center">{region.zone}</td>

        <td className="text-center">
          <img
            height="65"
            alt={region.provider}
            src={`${process.env.PUBLIC_URL}/icons/${region.provider}.png`}
          />
        </td>

        <td className="col-md-2">
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"updateRegion " + region.name}
            onClick={() => history.push("/region/update/" + region.id)}
          >
            <FontAwesomeIcon icon={faEdit} />
          </Button>
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"deleteRegion " + region.name}
            onClick={() => deleteRegion(region.id)}
          >
            <FontAwesomeIcon icon={faTrash} />
          </Button>
        </td>
      </tr>
    ) : (
      <></>
    )
  );
};

export default RegionTableContent;
