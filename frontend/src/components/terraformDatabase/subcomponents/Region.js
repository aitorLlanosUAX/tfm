import React from "react";
import { Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import ProviderContext from "../ProviderContext";
import { useTranslation } from "react-i18next";

const Region = (props) => {
  const history = useHistory();
  const regions = props.regions;
  const { stateProvider, setStateProvider } = React.useContext(ProviderContext);
  const { t } = useTranslation();

  const submit = (region) => {
    setStateProvider({ ...stateProvider, region: region });
    sessionStorage.setItem("region", region.id);
    history.push(`/provider/image/${region.id}/${stateProvider.provider.id}`);
  };

  return regions.map((region) => (
    <tr className="align-items-center" key={region.id}>
      <td className="text-center">{region.name}</td>
      <td className="text-center">{region.zone}</td>
      <td className="col-md-1 ">
        <Button
          className="align-items-end"
          variant="primary"
          type="submit"
          id="selectRegion"
          onClick={() => submit(region)}
        >
          {t("Select")}
        </Button>
      </td>
    </tr>
  ));
};

export default Region;
