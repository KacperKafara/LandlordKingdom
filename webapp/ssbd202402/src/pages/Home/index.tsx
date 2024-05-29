import { FC } from "react";
import homeImage from "@/assets/home_image.jpg";
import { Button } from "@/components/ui/button";
import { NavLink, Navigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import LanguageSelector from "@/components/LanguageSelector";
import { useUserStore } from "@/store/userStore";
import { ModeToggle } from "@/components/ui/toggle-theme";

const HomePage: FC = () => {
  const { t } = useTranslation();
  const { token, refreshToken } = useUserStore();

  if (token !== undefined || refreshToken !== undefined) {
    return <Navigate to="/login" />;
  }

  return (
    <div className="relative flex min-h-screen w-screen flex-col items-center justify-center">
      <LanguageSelector />
      <div className="absolute right-14 top-1 w-fit self-end">
        <ModeToggle />
      </div>
      <div className="flex flex-row items-center">
        <img
          src={homeImage}
          className="hidden w-[400px] rounded-md shadow-2xl shadow-shadowColor sm:block lg:w-[600px]"
        />
        <div className="flex aspect-square w-[300px] flex-col items-center justify-center gap-2 rounded-md bg-card p-10 shadow-2xl shadow-shadowColor sm:-translate-x-10 lg:w-[500px]">
          <h1 className="text-center text-4xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <p className="mb-10 text-center text-lg">
            {t("homePage.manageProperties")}
          </p>
          <Button variant="outline" asChild>
            <NavLink to="/register">{t("homePage.signUp")}</NavLink>
          </Button>
          {t("homePage.or")}
          <Button asChild>
            <NavLink to="/login">{t("homePage.signIn")}</NavLink>
          </Button>
        </div>
      </div>
    </div>
  );
};

export default HomePage;
