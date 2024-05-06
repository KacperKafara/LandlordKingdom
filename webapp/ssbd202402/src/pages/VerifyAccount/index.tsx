import { useVerifyAccount } from "@/data/useVerifyAccount";
import { FC, useEffect } from "react";
import { Navigate } from "react-router-dom";

const VerifyAccountPage: FC = () => {
  const { verifyAccount } = useVerifyAccount();

  useEffect(() => {
    verifyAccount();
  }, [verifyAccount]);

  return <Navigate to={"/login"} />;
};

export default VerifyAccountPage;
