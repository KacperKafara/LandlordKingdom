import { RouteObject } from "react-router-dom";
import AdminLayout from "./Layouts/AdminLayout";
import OwnerLayout from "./Layouts/OwnerLayout";
import TenantLayout from "./Layouts/TenantLayout";
import AccountLayout from "./Layouts/AccountLayout";
import AdminTestPage from "./pages/Admin/Test";
import OwnerTestPage from "./pages/Owner/Test";
import TenantTestPage from "./pages/Tenant/Test";
import loadable from "@loadable/component";

const UserDetailsPage = loadable(() => import("./pages/Admin/UserDetailsPage"));
const MePage = loadable(() => import("./pages/Me"));
const UserListPage = loadable(() => import("./pages/Admin/UserListPage"));
const LoginPage = loadable(() => import("./pages/Login"));
const RegisterPage = loadable(() => import("./pages/Register"));
const RegistrationSuccessPage = loadable(
  () => import("./pages/RegistrationSuccess")
);
const ResetPasswordPage = loadable(
  () => import("./pages/User/ResetPasswordPage")
);
const VerifyAccountPage = loadable(() => import("./pages/VerifyAccount"));
const UpdateEmailPage = loadable(() => import("./pages/UpdateEmail"));
const ResetPasswordForm = loadable(() => import("./pages/ResetPasswordForm"));
const Callback = loadable(() => import("./pages/OauthCallback"));

const AdminRoutes: RouteObject[] = [
  { path: "test", Component: AdminTestPage },
  { path: "users", Component: UserListPage },
  { path: "users/user/:id", Component: UserDetailsPage },
];
const OwnerRoutes: RouteObject[] = [{ path: "test", Component: OwnerTestPage }];
const TenantRoutes: RouteObject[] = [
  { path: "test", Component: TenantTestPage },
];
const AccountRoutes: RouteObject[] = [{ index: true, Component: MePage }];

export const UnprotectedRoutes: RouteObject[] = [
  { path: "/login", Component: LoginPage },
  { path: "/register", Component: RegisterPage },
  { path: "/register-success", Component: RegistrationSuccessPage },
  { path: "/reset-password", Component: ResetPasswordPage },
  { path: "/verify/:token", Component: VerifyAccountPage },
  { path: "/update-email/:token", Component: UpdateEmailPage },
  { path: "/reset-password-form", Component: ResetPasswordForm },
  { path: "/auth/google/callback", Component: Callback },
];

export const ProtectedRoutes: RouteObject[] = [
  { path: "admin", Component: AdminLayout, children: AdminRoutes },
  { path: "owner", Component: OwnerLayout, children: OwnerRoutes },
  { path: "tenant", Component: TenantLayout, children: TenantRoutes },
  { path: "account", Component: AccountLayout, children: AccountRoutes },
];
