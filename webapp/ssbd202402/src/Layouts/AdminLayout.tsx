import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "@/store/userStore";

const links: NavigationLink[] = [
  { label: "test1", path: "test1" },
  { label: "test2", path: "test1" },
  { label: "test3", path: "test1" },
];

const AdminLayout: FC = () => {
  const { roles } = useUserStore();
  if (!roles?.includes("ADMINISTRATOR")) {
    return <Navigate to={"/error"} />;
  }

  return (
    <BaseLayout type="admin" links={links}>
      <Outlet />
    </BaseLayout>
  );
};

export default AdminLayout;
