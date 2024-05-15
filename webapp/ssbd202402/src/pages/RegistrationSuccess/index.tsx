import LanguageSelector from "@/components/LanguageSelector";
import { Button } from "@/components/ui/button";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

const RegistrationSuccessPage: FC = () => {
  const { t } = useTranslation();
  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <div className="border-1 bg-white rounded-md border-black p-7 w-1/4 flex flex-col shadow-2xl gap-3 relative">
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
