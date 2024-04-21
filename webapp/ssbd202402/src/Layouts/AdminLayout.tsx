import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Outlet } from "react-router-dom";

const links: NavigationLink[] = [
  { label: "test1", path: "test1" },
  { label: "test2", path: "test1" },
  { label: "test3", path: "test1" },
];

const AdminLayout: FC = () => {
  return (
    <BaseLayout type="admin" links={links}>
      <Outlet />
    </BaseLayout>
  );
};

export default AdminLayout;
