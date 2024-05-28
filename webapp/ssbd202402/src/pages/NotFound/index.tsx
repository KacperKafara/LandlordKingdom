import React from "react";
import { useTranslation } from "react-i18next";

const NotFound: React.FC = () => {
  const { t } = useTranslation();
  return (
    <div className="flex min-h-screen items-center justify-center">
      <div className="border-1 relative flex w-1/4 min-w-fit flex-col rounded-md bg-card p-7 pt-9 shadow-2xl shadow-shadowColor">
        <h1 className="text-2xl">{t("notFoundPage.title")}</h1>
        <p>{t("notFoundPage.description")}</p>
      </div>
    </div>
  );
};

export default NotFound;
