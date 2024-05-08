import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { useResetMyEmailAddress } from "@/data/useUpdateEmailAddress.ts";

const MePage: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { updateEmail } = useResetMyEmailAddress();

  const updateEmailAddressClick = async () => {
    await updateEmail();
  };

  return (
    <>
      <div className="flex gap-2 justify-center mt-5">
        <Button variant="outline" onClick={() => navigate("info")}>
          {t("mePage.accountInfo")}
        </Button>
        <Button variant="outline" onClick={() => updateEmailAddressClick()}>
          {t("mePage.updateEmailAddress")}
        </Button>
      </div>
    </>
  );
};

export default MePage;
