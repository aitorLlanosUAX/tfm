import React, { useState, useEffect } from "react";
import { Button } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTrash, faEdit } from "@fortawesome/free-solid-svg-icons";
import { useNavigate } from "react-router-dom";

const ImageIsoTableContent = (props) => {
  const images = props.images;
  const [isLoaded, setIsLoaded] = useState(false);
  const [isRegionLoaded, setIsRegionLoaded] = useState(false);
  const navigate = useNavigate();

  const deleteImage = (imageId) => {
    let urlSearch = new URL("http://localhost:8080/image/delete");
    let params = {
      id: imageId,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "POST",
    })
      .then((res) => res.json())
      .then((result) => {
        navigate("/dashboard");
        navigate("/image/list");
      })
      .catch(console.log("Network Error"));
  };

  const searchByProviderId = (image) => {
    let urlSearch = new URL("http://localhost:8080/provider/findById");
    let params = {
      id: image.provider_id,
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
        image.provider = result.name;
      })
      .catch(console.log("Network Error"));
  };

  const searchByRegionId = (image) => {
    let urlSearch = new URL("http://localhost:8080/region/findById");
    let params = {
      id: image.region_id,
    };
    Object.keys(params).forEach((key) =>
      urlSearch.searchParams.append(key, params[key])
    );
    return fetch(urlSearch, {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsRegionLoaded(true);
        image.region = result.name;
      })
      .catch(console.log("Network Error"));
  };

  useEffect(() => {
    images.forEach((image) => searchByProviderId(image));
    images.forEach((image) => searchByRegionId(image));
  }, [images]);

  return images.map((image) =>
    isLoaded && isRegionLoaded ? (
      <tr className="align-items-center" key={image.id}>
        <td className="text-center">{image.name}</td>
        <td className="text-center">{image.operatingSystem}</td>
        <td className="text-center">
          <img
            height="65"
            alt={image.provider}
            src={`${process.env.PUBLIC_URL}/icons/${image.provider}.png`}
          />
        </td>
        <td className="text-center">{image.region}</td>
        <td className="col-md-2">
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"updateImage " + image.name}
            onClick={() => navigate("/image/update/" + image.id)}
          >
            <FontAwesomeIcon icon={faEdit} />
          </Button>
          <Button
            className="mx-1"
            variant="primary"
            type="submit"
            id={"deleteImage " + image.name}
            onClick={() => deleteImage(image.id)}
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

export default ImageIsoTableContent;
