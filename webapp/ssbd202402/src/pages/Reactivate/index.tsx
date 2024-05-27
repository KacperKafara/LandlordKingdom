import { useReactivateUser } from "@/data/useReactivateUser";
import { FC, useEffect, useRef } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";

const Reactivate: FC = () => {
  const used = useRef(false);
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");
  const { reactivate } = useReactivateUser();
  const navigate = useNavigate();

  useEffect(() => {
    (async () => {
      if (used.current || !token) return;
      used.current = true;
      await reactivate(token);
      navigate("/login");
    })();
  }, [reactivate, token, navigate]);
  return <></>;
};

export default Reactivate;
