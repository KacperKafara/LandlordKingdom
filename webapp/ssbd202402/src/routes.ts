import { RouteObject } from "react-router-dom";
import AdminLayout from "./Layouts/AdminLayout";
import OwnerLayout from "./Layouts/OwnerLayout";
import TenantLayout from "./Layouts/TenantLayout";
import AccountLayout from "./Layouts/AccountLayout";
import AdminTestPage from "./pages/Admin/Test";
import OwnerTestPage from "./pages/Owner/Test";
import TenantTestPage from "./pages/Tenant/Test";
import loadable from "@loadable/component";
import LocalsPage from "./pages/Admin/Locals";
import OwnLocalsPage from "./pages/Owner/Locals";
import NotApprovedActionsPage from "./pages/Admin/NotApprovedActions";

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
const HomePage = loadable(() => import("./pages/Home"));
const CurrentRentsPage = loadable(() => import("./pages/Tenant/CurrentRents"));
const CurrnetOwnerRentsPage = loadable(() => import("./pages/Owner/Rents"));
const ArchivalRentsPage = loadable(() => import("./pages/Tenant/ArchivalRents"));

const AdminRoutes: RouteObject[] = [
  { path: "locals", Component: LocalsPage },
  { path: "test", Component: AdminTestPage },
  { path: "users", Component: UserListPage },
  { path: "users/:id", Component: UserDetailsPage },
  { path: "not-approved", Component: NotApprovedActionsPage },
];
const OwnerRoutes: RouteObject[] = [
  { path: "test", Component: OwnerTestPage },
  { path: "locals", Component: OwnLocalsPage },
  { path: "rents", Component: CurrnetOwnerRentsPage },
];
const TenantRoutes: RouteObject[] = [
  { path: "test", Component: TenantTestPage },
  { path: "current-rents", Component: CurrentRentsPage },
  { path: "archival-rents", Component: ArchivalRentsPage },
];
const AccountRoutes: RouteObject[] = [{ index: true, Component: MePage }];

export const UnprotectedRoutes: RouteObject[] = [
  { index: true, Component: HomePage },
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
