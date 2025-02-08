import LanguageSelector from "@/components/LanguageSelector";
import LoadingButton from "@/components/LoadingButton";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { ModeToggle } from "@/components/ui/toggle-theme";
import { useResetPassword } from "@/data/useResetPassword";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { NavLink, useNavigate } from "react-router-dom";
import { z } from "zod";

const getResetPasswordSchema = (t: TFunction) =>
  z.object({
    email: z
      .string()
      .email(t("resetPasswordForm.emailRequired"))
      .min(
        5,
        t("validation.minLength") +
          " " +
          5 +
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
  });

type ResetPasswordSchema = z.infer<ReturnType<typeof getResetPasswordSchema>>;

const ResetPasswordForm: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const resetPassword = useResetPassword();
  const form = useForm<ResetPasswordSchema>({
    resolver: zodResolver(getResetPasswordSchema(t)),
    values: {
      email: "",
    },
  });

  const onSubmit = form.handleSubmit(async (values) => {
    await resetPassword.mutateAsync(values, {
      onSuccess: () => {
        navigate("/login");
      },
    });
  });

  return (
    <div className="flex min-h-screen items-center justify-center">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="bg-card border-1 shadow-2xl shadow-shadowColor min-w-fit relative flex w-1/4 flex-col rounded-md p-7 pt-9"
        >
          <LanguageSelector />
          <div className="w-fit self-end absolute right-14 top-1">
            <ModeToggle />
          </div>
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center pb-7 pt-3 text-2xl">
            {t("resetPasswordForm.resetPassword")}
          </h2>
          <p className="mb-2">{t("resetPasswordForm.description")}</p>
          <FormField
            control={form.control}
            name="email"
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("resetPasswordForm.email")}</FormLabel>
                <FormControl>
                  <Input {...field} autoComplete="email" type="email" />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <LoadingButton
            type="submit"
            className="mt-3"
            text={t("resetPasswordForm.resetPassword")}
            isLoading={resetPassword.isPending}
          />
          <Button variant="link" asChild className="w-fit self-center">
            <NavLink to={"/login"}>
              {t("resetPasswordForm.loginButton")}
            </NavLink>
          </Button>
        </form>
      </Form>
    </div>
  );
};

export default ResetPasswordForm;
