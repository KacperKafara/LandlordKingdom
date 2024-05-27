import { FC } from "react";
import BaseLayout from "./BaseLayout";
import { Outlet } from "react-router-dom";

const AccountLayout: FC = () => {
  return (
    <BaseLayout type="me">
      <Outlet />
    </BaseLayout>
  );
};

export default AccountLayout;
