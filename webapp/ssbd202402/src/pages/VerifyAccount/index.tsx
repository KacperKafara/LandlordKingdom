import { useVerifyAccount } from "@/data/useVerifyAccount";
import { FC, useEffect, useRef } from "react";
import { Navigate } from "react-router-dom";

const VerifyAccountPage: FC = () => {
  const called = useRef(false);
  const { verifyAccount } = useVerifyAccount();

  useEffect(() => {
    if (called.current) return;
    called.current = true;
    verifyAccount();
  }, [verifyAccount]);

  return <Navigate to={"/login"} />;
};

export default VerifyAccountPage;
