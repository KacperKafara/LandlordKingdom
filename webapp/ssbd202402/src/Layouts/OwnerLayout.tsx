import { FC } from "react";
import BaseLayout, { NavigationLink } from "./BaseLayout";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "@/store/userStore";
import { TFunction } from "i18next";
import { useTranslation } from "react-i18next";

const links = (t: TFunction): NavigationLink[] => [
  { path: "locals", label: t("navLinks.locals") },
  { path: "current-rents", label: t("navLinks.currentRents") },
  { path: "archival-rents", label: t("navLinks.archivalRents") },
];

const OwnerLayout: FC = () => {
  const { t } = useTranslation();
  const { roles, activeRole } = useUserStore();
  if (!roles?.includes("OWNER")) {
    return <Navigate to={"/error"} />;
  }

  if (activeRole !== "OWNER") {
    return <Navigate to={"/error"} />;
  }

  return (
    <BaseLayout type="owner" links={links(t)}>
      <Outlet />
    </BaseLayout>
  );
};

export default OwnerLayout;
