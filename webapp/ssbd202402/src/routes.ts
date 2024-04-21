import { RouteObject } from "react-router-dom";
import AdminLayout from "./Layouts/AdminLayout";
import OwnerLayout from "./Layouts/OwnerLayout";
import TenantLayout from "./Layouts/TenantLayout";
import AccountLayout from "./Layouts/AccountLayout";
import AdminTestPage from "./pages/AdminTest";

const AdminRoutes: RouteObject[] = [{ path: "test", Component: AdminTestPage }];
const OwnerRoutes: RouteObject[] = [];
const TenantRoutes: RouteObject[] = [];
const AccountRoutes: RouteObject[] = [];

export const UnprotectedRoutes: RouteObject[] = [];

export const ProtectedRoutes: RouteObject[] = [
  { path: "admin", Component: AdminLayout, children: AdminRoutes },
  { path: "owner", Component: OwnerLayout, children: OwnerRoutes },
  { path: "tenant", Component: TenantLayout, children: TenantRoutes },
  { path: "account", Component: AccountLayout, children: AccountRoutes },
];
