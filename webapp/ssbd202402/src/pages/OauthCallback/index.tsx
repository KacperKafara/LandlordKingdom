import { useToast } from "@/components/ui/use-toast";
import { api } from "@/data/api";
import { useUserStore } from "@/store/userStore";
import { AuthenticateResponse } from "@/types/AuthenticateResponse";
import { FC, useEffect, useRef } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";

const role_mapping: { [key: string]: string } = {
  ADMINISTRATOR: "admin",
  TENANT: "tenant",
  OWNER: "owner",
};

const Callback: FC = () => {
  const called = useRef(false);
  const navigate = useNavigate();
  const { setToken, setRefreshToken, token, roles } = useUserStore();
  const { toast } = useToast();
  const { t } = useTranslation();

  useEffect(() => {
    (async () => {
      if (!token) {
        try {
          if (called.current) return;
          called.current = true;
          const result = await api.get<AuthenticateResponse>(
            `/auth/oauth2/token${window.location.search}`
          );

          if (result.status === 201) {
            navigate("/register-success");
            return;
          }

          setToken(result.data.token);
          setRefreshToken(result.data.refreshToken);
        } catch (err) {
          toast({
            variant: "destructive",
            title: t("loginPage.loginError"),
            description: t("loginPage.tryAgain"),
          });
        }
      } else {
        navigate("/");
      }

      if (roles == undefined) {
        navigate("/login");
      } else {
        navigate(`/${role_mapping[roles![0]]}`);
      }
    })();
  }, [navigate, token]);

  return <></>;
};

export default Callback;
