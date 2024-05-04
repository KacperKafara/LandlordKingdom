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
import MePage from "@/pages/Me";
import UserDataPage from "@/pages/Me/UserData";
import UserDetailsPage from "./pages/Admin/UserDetailsPage";
import ResetPasswordPage from "./pages/User/ResetPasswordPage";

const AdminRoutes: RouteObject[] = [
  { path: "test", Component: AdminTestPage },
  { path: "users", Component: UserListPage },
  { path: "users/user/:id", Component: UserDetailsPage }
];
const OwnerRoutes: RouteObject[] = [{ path: "test", Component: OwnerTestPage }];
const TenantRoutes: RouteObject[] = [
  { path: "test", Component: TenantTestPage },
];
const AccountRoutes: RouteObject[] = [
    {path: "", Component: MePage},
    {path: "info", Component: UserDataPage},
];

export const UnprotectedRoutes: RouteObject[] = [
  { path: "/login", Component: LoginPage },
  { path: "/register", Component: RegisterPage },
  { path: "/reset-password", Component: ResetPasswordPage },
];

export const ProtectedRoutes: RouteObject[] = [
  { path: "admin", Component: AdminLayout, children: AdminRoutes },
  { path: "owner", Component: OwnerLayout, children: OwnerRoutes },
  { path: "tenant", Component: TenantLayout, children: TenantRoutes },
  { path: "account", Component: AccountLayout, children: AccountRoutes },
];
