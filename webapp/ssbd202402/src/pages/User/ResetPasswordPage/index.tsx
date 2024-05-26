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
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { FC, useEffect } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { NavLink, useNavigate, useSearchParams } from "react-router-dom";
import { z } from "zod";
import { useChangeUserPasswordWithToken } from "@/data/useChangeUserPassword";
import LoadingButton from "@/components/LoadingButton";

const getPasswordResetSchema = (t: TFunction) =>
  z
    .object({
      password: z
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
    .refine((data) => data.password === data.confirmPassword, {
      message: t("registerPage.passwordMatch"),
      path: ["confirmPassword"],
    });

type PasswordResetSchema = z.infer<ReturnType<typeof getPasswordResetSchema>>;

const ResetPasswordPage: FC = () => {
  const navigate = useNavigate();
  const { t } = useTranslation();
  const changePassword = useChangeUserPasswordWithToken();
  const [searchParams] = useSearchParams();
  const token = searchParams.get("token");

  useEffect(() => {
    if (token === null || token === undefined || token === "") {
      navigate("/");
    }
  }, [navigate, token]);

  const form = useForm<PasswordResetSchema>({
    resolver: zodResolver(getPasswordResetSchema(t)),
    values: {
      password: "",
      confirmPassword: "",
    },
  });

  const onSubmit = form.handleSubmit(async (values) => {
    await changePassword.mutateAsync(
      {
        password: values.password,
        token: token || "",
      },
      {
        onSuccess: () => {
          navigate("/login");
        },
      }
    );
  });

  return (
    <div className="flex min-h-screen items-center justify-center">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-1 flex w-1/4 flex-col rounded-md p-7 shadow-2xl"
        >
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center pb-7 pt-3 text-2xl">
            {t("resetPasswordPage.header")}
          </h2>
          <div className="grid gap-2">
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("resetPasswordPage.password")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="password" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="confirmPassword"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    {t("resetPasswordPage.confirmPassword")}
                  </FormLabel>
                  <FormControl>
                    <Input {...field} type="password" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <LoadingButton
              type="submit"
              text={t("resetPasswordPage.confirmButton")}
              isLoading={changePassword.isPending}
            />
            <div className="flex justify-center">
              <Button variant="link" asChild className="w-fit self-center">
                <NavLink to={"/"}>{t("resetPasswordPage.homeButton")}</NavLink>
              </Button>
            </div>
          </div>
        </form>
      </Form>
    </div>
  );
};

export default ResetPasswordPage;
