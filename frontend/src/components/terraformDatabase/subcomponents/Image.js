import React from "react";
import { Button } from "react-bootstrap";
import { useNavigate } from "react-router-dom";
import ProviderContext from "../ProviderContext";
import { useTranslation } from "react-i18next";

const Image = (props) => {
  const navigate = useNavigate();
  const images = props.images;
  const { stateProvider, setStateProvider } = React.useContext(ProviderContext);
  const { t } = useTranslation();

  const submit = (image) => {
    setStateProvider({ ...stateProvider, image: image });
    sessionStorage.setItem("image", image.id);
    navigate(`/provider/instance/${image.id}`);
  };

  return images.map((image) => (
    <tr className="align-items-center" key={image.id}>
      <td className="text-center">{image.name}</td>
      <td className="text-center">{image.operatingSystem}</td>
      <td className="col-md-1 ">
        <Button
          className="align-items-end"
          variant="primary"
          type="submit"
          id="selectImage"
          onClick={() => submit(image)}
        >
          {t("Select")}
        </Button>
      </td>
    </tr>
  ));
};

export default Image;
