import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useResetMyEmailAddress } from "@/data/useUpdateEmailAddress.ts";
import {
  AlertDialog,
  AlertDialogTrigger,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/alert-dialog";

const UpdateUserEmailMyAddress: FC = () => {
  const { t } = useTranslation();
  const { updateEmail } = useResetMyEmailAddress();

  const updateEmailAddressClick = async () => {
    await updateEmail();
  };

  return (
    <>
      <AlertDialog>
        <AlertDialogTrigger asChild>
          <Button variant={"ghost"} className="font-normal px-2 py-[6px] h-8">
            {t("userListPage.resetUserEmailAction")}
          </Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>
              {t("userListPage.updateEmailAddressTitle")}
            </AlertDialogTitle>
            <AlertDialogDescription>
              {t("userListPage.updateEmailAddressDescription")}
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
            <AlertDialogAction asChild>
              <Button type="submit" onClick={() => updateEmailAddressClick()}>
                {t("confirm")}
              </Button>
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default UpdateUserEmailMyAddress;
