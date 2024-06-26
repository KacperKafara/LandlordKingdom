import ConfirmDialog from "@/components/ConfirmDialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useChangeUserPassword } from "@/data/useChangeUserPassword";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { z } from "zod";

const passwordChangeSchema = (t: TFunction) =>
  z
    .object({
      oldPassword: z
        .string()
        .min(
          8,
          t("validation.minLength") +
            " " +
            8 +
            " " +
            t("validation.characters") +
            "."
        )
        .max(
          50,
          t("validation.maxLength") +
            " " +
            50 +
            " " +
            t("validation.characters") +
            "."
        ),
      newPassword: z
        .string()
        .min(
          8,
          t("validation.minLength") +
            " " +
            8 +
            " " +
            t("validation.characters") +
            "."
        )
        .max(
          50,
          t("validation.maxLength") +
            " " +
            50 +
            " " +
            t("validation.characters") +
            "."
        ),
      confirmPassword: z
        .string()
        .min(
          8,
          t("validation.minLength") +
            " " +
            8 +
            " " +
            t("validation.characters") +
            "."
        )
        .max(
          50,
          t("validation.maxLength") +
            " " +
            50 +
            " " +
            t("validation.characters") +
            "."
        ),
    })
    .refine((data) => data.newPassword === data.confirmPassword, {
      message: t("registerPage.passwordMatch"),
      path: ["confirmPassword"],
    });

type PasswordChangeSchema = z.infer<ReturnType<typeof passwordChangeSchema>>;

const ChangeUserPassword: FC = () => {
  const { t } = useTranslation();
  const { changePassword } = useChangeUserPassword();
  const passwordChangeForm = useForm<PasswordChangeSchema>({
    resolver: zodResolver(passwordChangeSchema(t)),
    values: {
      oldPassword: "",
      newPassword: "",
      confirmPassword: "",
    },
  });
  const handlePasswordChangeSubmit = passwordChangeForm.handleSubmit(
    async (values) => {
      await changePassword(values, {
        onSuccess: () => {
          passwordChangeForm.reset();
        },
      });
    }
  );
  return (
    <Form {...passwordChangeForm}>
      <form
        onSubmit={handlePasswordChangeSubmit}
        className="flex w-3/4 flex-col gap-3"
      >
        <FormField
          control={passwordChangeForm.control}
          name="oldPassword"
          render={({ field }) => (
            <FormItem>
              <FormLabel>{t("changePasswordForm.oldPassword")}</FormLabel>
              <FormControl>
                <Input
                  {...field}
                  type="password"
                  autoComplete="current-password"
                />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={passwordChangeForm.control}
          name="newPassword"
          render={({ field }) => (
            <FormItem>
              <FormLabel>{t("changePasswordForm.newPassword")}</FormLabel>
              <FormControl>
                <Input {...field} type="password" autoComplete="new-password" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={passwordChangeForm.control}
          name="confirmPassword"
          render={({ field }) => (
            <FormItem>
              <FormLabel>{t("changePasswordForm.confirmPassword")}</FormLabel>
              <FormControl>
                <Input {...field} type="password" autoComplete="new-password" />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <ConfirmDialog
          className="mt-5"
          buttonText={t("common.update")}
          dialogTitle={t("changePasswordForm.alertDialogTitle")}
          dialogDescription={t("changePasswordForm.alertDialogDescription")}
          confirmAction={() => handlePasswordChangeSubmit()}
        />
      </form>
    </Form>
  );
};

export default ChangeUserPassword;
