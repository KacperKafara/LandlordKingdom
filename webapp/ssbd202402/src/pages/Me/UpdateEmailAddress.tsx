
import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useResetMyEmailAddress } from "@/data/useUpdateEmailAddress.ts";
import { AlertDialog, AlertDialogTrigger, AlertDialogContent, AlertDialogHeader, AlertDialogTitle, AlertDialogDescription, AlertDialogFooter, AlertDialogCancel, AlertDialogAction } from "@/components/ui/alert-dialog";

const updateEmailMyAddress: FC = () => {
    const { t } = useTranslation();
    const { updateEmail } = useResetMyEmailAddress();

    const updateEmailAddressClick = async () => {
        await updateEmail();
    };

    return (
        <>
            <AlertDialog>
                <AlertDialogTrigger asChild>
                    <Button variant={"outline"}>{t("mePage.updateEmailAddress")}</Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                    <AlertDialogHeader>
                        <AlertDialogTitle>
                            {t("mePage.updateEmailAddressTitle")}
                        </AlertDialogTitle>
                        <AlertDialogDescription>
                            {t("mePage.updateEmailAddressDescription")}
                        </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                        <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
                        <AlertDialogAction asChild>
                            <Button
                                type="submit"
                                onClick={() => updateEmailAddressClick()}
                            >
                                {t("confirm")}
                            </Button>
                        </AlertDialogAction>
                    </AlertDialogFooter>
                </AlertDialogContent>
            </AlertDialog>
        </>
    );
};

export default updateEmailMyAddress;