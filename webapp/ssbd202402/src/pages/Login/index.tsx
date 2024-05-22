import { FC, useState } from "react";
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
import { NavLink, Navigate } from "react-router-dom";
import i18next, { TFunction } from "i18next";
import { isTokenValid } from "@/utils/jwt";
import CodeInput from "@/pages/Login/CodeInput";
import { Loader2 } from "lucide-react";
import GoogleLoginButton from "@/components/GoogleLoginButton";
import LanguageSelector from "@/components/LanguageSelector";
import LoadingButton from "@/components/LoadingButton";

const getLoginSchema = (t: TFunction) =>
  z.object({
    login: z
      .string()
      .min(
        3,
        t("validation.minLength") +
          " " +
          3 +
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
  });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const Login2FaPage: FC = () => {
  const { t } = useTranslation();
  const { setToken, setRefreshToken, token, roles } = useUserStore();
  const { authenticate, isPending } = useAuthenticate();
  const [login, setLogin] = useState<string>();
  const [codeInputOpen, setCodeInputOpen] = useState(false);
  const form = useForm<LoginSchema>({
    resolver: zodResolver(getLoginSchema(t)),
    values: {
      login: "",
      password: "",
    },
  });

  const role_mapping: { [key: string]: string } = {
    ADMINISTRATOR: "admin",
    TENANT: "tenant",
    OWNER: "owner",
  };

  const onSubmit = form.handleSubmit(async ({ login, password }) => {
    await authenticate({ login, password, language: i18next.language });
    setCodeInputOpen(true);
    setLogin(login);
  });

  if (token && isTokenValid(token)) {
    return <Navigate to={`/${role_mapping[roles![0]]}`} replace />;
  }

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      {codeInputOpen ? (
        <CodeInput
          login={login || ""}
          roles={roles}
          setToken={setToken}
          setCodeInputOpen={setCodeInputOpen}
          setRefreshToken={setRefreshToken}
          resetForm={form.reset}
        />
      ) : (
        <Form {...form}>
          <form
            onSubmit={onSubmit}
            className="border-1 relative flex w-1/4 flex-col rounded-md border-black bg-white p-7 shadow-2xl"
          >
            <div className="flex justify-center">
              <h1 className="w-fit text-3xl font-bold">
                {t("logoPlaceholder")}
              </h1>
              <LanguageSelector />
            </div>
            <h2 className="self-center pb-7 pt-3 text-2xl">
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
                <FormItem className="mb-2">
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
              to={"/reset-password-form"}
              className="self-end pb-2 text-sm text-slate-600"
            >
              {t("loginPage.forgotPassword")}
            </NavLink>
            <div className="flex flex-col gap-3">
              <LoadingButton
                type="submit"
                isLoading={isPending}
                text={t("loginPage.loginButton")}
              />
              <GoogleLoginButton />
              <Button variant="link" asChild className="w-fit self-center">
                <NavLink to={"/register"}>{t("loginPage.register")}</NavLink>
              </Button>
            </div>
          </form>
        </Form>
      )}
    </div>
  );
};

export default Login2FaPage;
