import { FC } from "react";
import BaseLayout from "./BaseLayout";
import { Outlet } from "react-router-dom";

const AdminLayout: FC = () => {
  return (
    <BaseLayout type="admin">
      <Outlet />
    </BaseLayout>
  );
};

export default AdminLayout;
