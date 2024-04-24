import { RouteObject } from "react-router-dom";
import AdminLayout from "./Layouts/AdminLayout";
import OwnerLayout from "./Layouts/OwnerLayout";
import TenantLayout from "./Layouts/TenantLayout";
import AccountLayout from "./Layouts/AccountLayout";
import AdminTestPage from "./pages/Admin/Test";
import LoginPage from "./pages/Login";
import RegisterPage from "./pages/Register";
import OwnerTestPage from "./pages/Owner/Test";
import TenantTestPage from "./pages/Tenant/Test";
import UserListPage from "./pages/Admin/UserListPage";

const AdminRoutes: RouteObject[] = [
  { path: "test", Component: AdminTestPage },
  { path: "users", Component: UserListPage },
];
const OwnerRoutes: RouteObject[] = [{ path: "test", Component: OwnerTestPage }];
const TenantRoutes: RouteObject[] = [
  { path: "test", Component: TenantTestPage },
];
const AccountRoutes: RouteObject[] = [];

export const UnprotectedRoutes: RouteObject[] = [
  { path: "/login", Component: LoginPage },
  { path: "/register", Component: RegisterPage },
];

export const ProtectedRoutes: RouteObject[] = [
  { path: "admin", Component: AdminLayout, children: AdminRoutes },
  { path: "owner", Component: OwnerLayout, children: OwnerRoutes },
  { path: "tenant", Component: TenantLayout, children: TenantRoutes },
  { path: "account", Component: AccountLayout, children: AccountRoutes },
];
