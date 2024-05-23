import { FC } from "react";
import { useTranslation } from "react-i18next";
import { useResetMyEmailAddress } from "@/data/useUpdateEmailAddress.ts";
import ConfirmDialog from "@/components/ConfirmDialog";

const UpdateEmailMyAddress: FC = () => {
  const { t } = useTranslation();
  const { updateEmail } = useResetMyEmailAddress();

  const updateEmailAddressClick = async () => {
    await updateEmail();
  };

  return (
    <ConfirmDialog
      buttonText={t("mePage.updateEmailAddress")}
      dialogTitle={t("mePage.updateEmailAddressTitle")}
      dialogDescription={t("mePage.updateEmailAddressDescription")}
      confirmAction={() => updateEmailAddressClick()}
    />
  );
};

export default UpdateEmailMyAddress;
