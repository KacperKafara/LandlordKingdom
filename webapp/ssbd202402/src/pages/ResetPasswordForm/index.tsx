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
    email: z.string().email(t("resetPasswordForm.emailRequired")),
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
    resetPassword.mutate(values.email);

    if (resetPassword.isSuccess) {
      navigate("/login");
    }
  });

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-1 bg-white rounded-md border-black p-7 w-1/4 flex flex-col shadow-2xl"
        >
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center text-2xl pb-7 pt-3">
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
          <Button type="submit" className="mt-3">
            {t("resetPasswordForm.resetPassword")}
          </Button>
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
