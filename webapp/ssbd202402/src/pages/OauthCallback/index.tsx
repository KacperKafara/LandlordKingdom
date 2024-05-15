import { api } from "@/data/api";
import { useUserStore } from "@/store/userStore";
import { FC, useEffect, useRef } from "react";
import { useNavigate } from "react-router-dom";

const Callback: FC = () => {
  const called = useRef(false);
  const navigate = useNavigate();
  const { token } = useUserStore();

  useEffect(() => {
    (async () => {
      if (!token) {
        try {
          if (called.current) return;
          called.current = true;
          const res = await api.get(
            `/auth/oauth2/token${window.location.search}`
          );
          console.log(res);
        } catch (err) {
          console.log(err);
        }
      } else {
        navigate("/");
      }
    })();
  }, [navigate, token]);

  return <></>;
};

export default Callback;
