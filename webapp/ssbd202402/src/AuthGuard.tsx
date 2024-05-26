import { FC } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "./store/userStore";
import SessionExpiredDialog from "./components/SessionExpiredDialog";

const AuthGuard: FC = () => {
  const { token, refreshToken } = useUserStore();
  const isLoggedIn = token !== undefined || refreshToken !== undefined;

  return (
    <>
      {!isLoggedIn ? (
        <Navigate to={"/login"} replace={true} />
      ) : (
        <>
          <Outlet />
          <SessionExpiredDialog />
        </>
      )}
    </>
  );
};

export default AuthGuard;
