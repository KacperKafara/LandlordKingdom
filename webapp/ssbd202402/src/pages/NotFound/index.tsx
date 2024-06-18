import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { roleMapping, useUserStore } from "@/store/userStore";
import React from "react";
import { useTranslation } from "react-i18next";
import { NavLink } from "react-router-dom";

const NotFound: React.FC = () => {
  const { t } = useTranslation();
  const { activeRole } = useUserStore();

  return (
    <div className="flex min-h-screen items-center justify-center">
      <Card>
        <CardHeader>
          <CardTitle className="text-center text-4xl">
            {t("notFoundPage.title")}
          </CardTitle>
        </CardHeader>
        <CardContent className="flex-grow text-xl">
          {t("notFoundPage.description")}
          <div className="flex justify-around pt-3">
            {!activeRole && (
              <Button variant="link" asChild>
                <NavLink to={"/login"} replace>
                  {t("notFoundPage.login")}
                </NavLink>
              </Button>
            )}
            {activeRole && (
              <Button variant="link" asChild>
                <NavLink to={`/${roleMapping[activeRole!]}`} replace>
                  {t("notFoundPage.home")}
                </NavLink>
              </Button>
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default NotFound;
