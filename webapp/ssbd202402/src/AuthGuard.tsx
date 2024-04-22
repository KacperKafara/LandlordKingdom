import { FC } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "./store/userStore";
import { isTokenValid } from "./utils/jwt";

const AuthGuard: FC = () => {
  const { token } = useUserStore();
  const isLoggedIn = token && isTokenValid(token);
  return (
    <>{!isLoggedIn ? <Navigate to={"/login"} replace={true} /> : <Outlet />}</>
  );
};

export default AuthGuard;
