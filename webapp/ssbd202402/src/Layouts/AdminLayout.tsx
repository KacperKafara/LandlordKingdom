import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "@/store/userStore";
import { useTranslation } from "react-i18next";
import { TFunction } from "i18next";

const links = (t: TFunction): NavigationLink[] => [
  { label: t("navLinks.users"), path: "/admin/users" },
  { label: t("navLinks.notApprovedActions"), path: "/admin/not-approved" },
];

const AdminLayout: FC = () => {
  const { t } = useTranslation();
  const { roles, activeRole } = useUserStore();
  if (!roles?.includes("ADMINISTRATOR")) {
    return <Navigate to={"/error"} />;
  }

  if (activeRole !== "ADMINISTRATOR") {
    return <Navigate to={"/error"} />;
  }

  return (
    <BaseLayout type="admin" links={links(t)}>
      <Outlet />
    </BaseLayout>
  );
};

export default AdminLayout;
