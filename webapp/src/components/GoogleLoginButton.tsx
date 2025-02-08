import { FC } from "react";
import { Button } from "./ui/button";
import { FaGoogle } from "react-icons/fa";
import { useTranslation } from "react-i18next";
import { useOAuthUrl } from "@/data/useOAuthUrl";

const GoogleLoginButton: FC = () => {
  const { t } = useTranslation();
  const { oAuthUrl } = useOAuthUrl();
  return (
    <Button
      variant="outline"
      type="button"
      onClick={() => {
        window.location.href = oAuthUrl?.url || "";
      }}
    >
      <FaGoogle className="mr-2 h-4 w-4" />
      {t("loginPage.googleLoginButton")}
    </Button>
  );
};

export default GoogleLoginButton;
