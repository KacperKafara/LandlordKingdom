import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import UpdateEmailAddress from "./UpdateEmailAddress";

const MePage: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();


  return (
    <>
      <div className="flex gap-2 items-center justify-center mt-5">
        <Button variant="outline" onClick={() => navigate("info")}>
          {t("mePage.accountInfo")}
        </Button>
        <UpdateEmailAddress />
      </div>
    </>
  );
};

export default MePage;
