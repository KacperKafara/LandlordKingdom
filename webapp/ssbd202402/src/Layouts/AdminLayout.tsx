import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "@/store/userStore";

const links: NavigationLink[] = [{ label: "Users", path: "/admin/users" }];

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
