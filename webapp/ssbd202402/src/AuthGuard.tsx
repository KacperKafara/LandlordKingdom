import { FC } from "react";
import { Navigate, Outlet } from "react-router-dom";
import { useUserStore } from "./store/userStore";
import SessionExpiredDialog from "./components/SessionExpiredDialog";
import { isTokenValid } from "./utils/jwt";

const AuthGuard: FC = () => {
  const { token, refreshToken } = useUserStore();
  const isLoggedIn =
    token !== undefined ||
    (refreshToken !== undefined && isTokenValid(refreshToken));

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
