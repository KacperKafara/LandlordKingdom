import { FC } from "react";
import { useLeaveLocal } from "@/data/mol/useLeaveLocal";
import { useTranslation } from "react-i18next";
import { cn } from "@/lib/utils";
import { HTMLAttributes } from "react";
import ConfirmDialog from "@/components/ConfirmDialog";

type LeaveLocalProps = {
  id: string;
  disabled?: boolean;
} & HTMLAttributes<HTMLButtonElement>;

export const LeaveLocalButton: FC<LeaveLocalProps> = ({
  id,
  className,
  disabled = false,
  ...tags
}) => {
  const { mutate, isPending } = useLeaveLocal();
  const { t } = useTranslation();

  return (
    <>
      <ConfirmDialog
        variant="destructive"
        className={cn(className)}
        confirmAction={() => mutate(id)}
        {...tags}
        disabled={isPending || disabled}
        buttonText={t("leaveLocal.buttonText")}
        dialogTitle={t("leaveLocal.dialogTitle")}
        dialogDescription={t("leaveLocal.dialogDescription")}
      ></ConfirmDialog>
    </>
  );
};
