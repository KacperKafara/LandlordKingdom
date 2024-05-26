import { FC } from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { VariantProps } from "class-variance-authority";
import { Button, buttonVariants } from "./ui/button";
import { useTranslation } from "react-i18next";

interface Props
  extends React.ButtonHTMLAttributes<HTMLButtonElement>,
    VariantProps<typeof buttonVariants> {
  buttonText: string;
  dialogTitle?: string;
  dialogDescription?: string;
  confirmAction?: () => void;
  cancelAction?: () => void;
}

const ConfirmDialog: FC<Props> = ({
  buttonText,
  variant,
  dialogTitle,
  dialogDescription,
  confirmAction,
  cancelAction,
  ...props
}) => {
  const { t } = useTranslation();

  return (
    <>
      <AlertDialog>
        <AlertDialogTrigger asChild>
          <Button {...props} variant={variant}>
            {buttonText}
          </Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            {dialogTitle && <AlertDialogTitle>{dialogTitle}</AlertDialogTitle>}
            {dialogDescription && (
              <AlertDialogDescription>
                {dialogDescription}
              </AlertDialogDescription>
            )}
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel onClick={() => cancelAction?.()}>
              {t("cancel")}
            </AlertDialogCancel>
            <AlertDialogAction asChild>
              <Button onClick={() => confirmAction?.()}>{t("confirm")}</Button>
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default ConfirmDialog;
