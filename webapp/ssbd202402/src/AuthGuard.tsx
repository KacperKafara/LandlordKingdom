import { FC } from "react";
import { Navigate, Outlet } from "react-router-dom";

const AuthGuard: FC = () => {
  const isLoggedIn = true;
  return (
    <>{!isLoggedIn ? <Navigate to={"/login"} replace={true} /> : <Outlet />}</>
  );
};

export default AuthGuard;
