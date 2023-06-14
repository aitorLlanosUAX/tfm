import React, { useEffect, useState } from "react";
import Loader from "../util/Loader";
import Provider from "./subcomponents/Provider";

const ProviderPage = () => {
  const [providers, setProviders] = useState([]);
  const [isLoaded, setIsLoaded] = useState(false);
  useEffect(() => {
    fetch("http://localhost:8080/provider/list", {
      method: "GET",
    })
      .then((res) => res.json())
      .then((result) => {
        setIsLoaded(true);
        if (result.length === 0) {
          setIsLoaded(false);
          return;
        }
        setProviders(result);
      })
      .catch(console.log("Network Error"));
  }, []);

  return isLoaded ? (
    providers.map((provider) => <Provider key={provider.id} prov={provider} />)
  ) : (
    <Loader />
  );
};

export default ProviderPage;
