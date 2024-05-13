import { FC } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "./store/userStore";

const AuthGuard: FC = () => {
  const { token, refreshToken } = useUserStore();
  const isLoggedIn = token !== undefined || refreshToken !== undefined;

  return (
    <>{!isLoggedIn ? <Navigate to={"/login"} replace={true} /> : <Outlet />}</>
  );
};

export default AuthGuard;
