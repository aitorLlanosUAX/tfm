import React from "react";
import { Button } from "react-bootstrap";
import { useHistory } from "react-router-dom";
import ProviderContext from "../ProviderContext";
import { useTranslation } from "react-i18next";

const Instance = (props) => {
  const history = useHistory();
  const instances = props.instances;
  const { stateProvider, setStateProvider } = React.useContext(ProviderContext);
  const { t } = useTranslation();

  const submit = (instance) => {
    setStateProvider({ ...stateProvider, instance: instance });
    sessionStorage.setItem("instance", instance.id);
    history.push(`/provider/summary`);
  };

  return instances.map((instance) => (
    <tr className="align-items-center" key={instance.id}>
      <td className="text-center">{instance.name}</td>
      <td className="text-center">{instance.vCpu}</td>
      <td className="text-center">{instance.memory}</td>
      <td className="text-center">{instance.cost}</td>
      <td className="col-md-1 ">
        <Button
          className="align-items-end"
          variant="primary"
          type="submit"
          id="selectInstance"
          onClick={() => submit(instance)}
        >
          {t("Select")}
        </Button>
      </td>
    </tr>
  ));
};

export default Instance;
