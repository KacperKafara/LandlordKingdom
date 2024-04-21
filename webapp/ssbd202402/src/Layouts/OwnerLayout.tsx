import { FC } from "react";
import BaseLayout from "./BaseLayout";
import { Outlet } from "react-router-dom";

const OwnerLayout: FC = () => {
  return (
    <BaseLayout type="owner">
      <Outlet />
    </BaseLayout>
  );
};

export default OwnerLayout;
