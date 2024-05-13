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
import { useChangeUserPasswordWithToken } from "@/data/useChangeUserPasswordWithToken";

const getPasswordResetSchema = (t: TFunction) =>
  z
    .object({
      password: z.string().min(8, t("registerPage.passwordRequired")),
      confirmPassword: z.string().min(8, t("registerPage.passwordMatch")),
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
    changePassword.mutate({
      password: values.password,
      token: token || "",
    });

    if (changePassword.isSuccess) {
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
            <Button type="submit">
              {t("resetPasswordPage.confirmButton")}
            </Button>
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
