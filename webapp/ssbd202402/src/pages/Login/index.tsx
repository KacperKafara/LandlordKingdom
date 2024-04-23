import { FC } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useAuthenticate } from "@/data/useAuthenticate";
import { useUserStore } from "@/store/userStore";
import { NavLink, Navigate, useNavigate } from "react-router-dom";
import { isTokenValid } from "@/utils/jwt";
import { TFunction } from "i18next";
import { Toaster } from "@/components/ui/toaster";

const getLoginSchema = (t: TFunction) =>
  z.object({
    login: z.string().min(1, t("loginPage.loginRequired")),
    password: z.string().min(1, t("loginPage.passwordRequired")),
  });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const LoginPage: FC = () => {
  const { t } = useTranslation();
  const { setToken, token } = useUserStore();
  const { authenticate } = useAuthenticate();
  const navigate = useNavigate();
  const form = useForm<LoginSchema>({
    resolver: zodResolver(getLoginSchema(t)),
    values: {
      login: "",
      password: "",
    },
  });

  const onSubmit = form.handleSubmit(async (values) => {
    const result = await authenticate(values);
    setToken(result.token);
    navigate("/admin/test");
  });

  if (token && isTokenValid(token)) {
    return <Navigate to={"/admin/test"} />;
  }

  return (
    <div className="flex items-center justify-center min-h-screen">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-2 rounded-md border-black p-7 w-1/4 flex flex-col"
        >
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center text-2xl pb-7 pt-3">
            {t("loginPage.loginHeader")}
          </h2>
          <FormField
            control={form.control}
            name="login"
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("loginPage.login")}</FormLabel>
                <FormControl>
                  <Input {...field} autoComplete="username" />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("loginPage.password")}</FormLabel>
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
          <NavLink
            to={"/resetPassword"}
            className="text-sm text-slate-600 self-end pb-2"
          >
            {t("loginPage.forgotPassword")}
          </NavLink>
          <Button type="submit">{t("loginPage.loginButton")}</Button>
          <Button variant="link" asChild className="w-fit self-center">
            <NavLink to={"/register"}>{t("loginPage.register")}</NavLink>
          </Button>
        </form>
      </Form>
      <Toaster />
    </div>
  );
};

export default LoginPage;
