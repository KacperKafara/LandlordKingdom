import LanguageSelector from "@/components/LanguageSelector";
import { Button } from "@/components/ui/button";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

const RegistrationSuccessPage: FC = () => {
  const { t } = useTranslation();
  return (
    <div className="flex min-h-screen items-center justify-center">
      <div className="bg-card border-1 relative flex w-1/4 min-w-fit flex-col gap-3 rounded-md p-7 shadow-2xl shadow-shadowColor">
        <LanguageSelector />
        <h1 className="w-fit text-3xl font-bold">
          {t("registerSuccessPage.title")}
        </h1>
        <p>{t("registerSuccessPage.description")}</p>
        <Button variant="link" asChild>
          <NavLink to={"/login"}>
            {t("registerSuccessPage.loginButton")}
          </NavLink>
        </Button>
      </div>
    </div>
  );
};

export default RegistrationSuccessPage;
