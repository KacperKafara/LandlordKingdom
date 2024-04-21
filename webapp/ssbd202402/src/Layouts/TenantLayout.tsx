import { FC } from "react";
import BaseLayout from "./BaseLayout";
import { Outlet } from "react-router-dom";

const TenantLayout: FC = () => {
  return (
    <BaseLayout type="tenant">
      <Outlet />
    </BaseLayout>
  );
};

export default TenantLayout;
