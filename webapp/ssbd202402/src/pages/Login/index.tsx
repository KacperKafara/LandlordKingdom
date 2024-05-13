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
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { isTokenValid } from "@/utils/jwt";
import CodeInput from "@/pages/Login/CodeInput";
import { DropdownMenu } from "@radix-ui/react-dropdown-menu";
import {
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useLanguageStore } from "@/i18n/languageStore";
import { role_mapping } from "@/data/role_path_maping";
const getLoginSchema = (t: TFunction) =>
  z.object({
    login: z.string().min(1, t("loginPage.loginRequired")),
    password: z.string().min(1, t("loginPage.passwordRequired")),
  });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const LoginPage: FC = () => {
  const { t } = useTranslation();
  const { token, roles } = useUserStore();
  const { authenticate } = useAuthenticate();
  const { setLanguage } = useLanguageStore();

  const [codeInputOpen, setCodeInputOpen] = useState(false);
  const form = useForm<LoginSchema>({
    resolver: zodResolver(getLoginSchema(t)),
    values: {
      login: "",
      password: "",
    },
  });

  const onSubmit = form.handleSubmit(async ({ login, password }) => {
    try {
      await authenticate({ login, password, language: i18next.language });
      setCodeInputOpen(true);
    } catch (error) {
      const responseError = error as AxiosError;
      if (
        responseError.response?.status === 401 ||
        responseError.response?.status === 404
      ) {
        toast({
          variant: "destructive",
          title: t("loginPage.loginError"),
          description: t("loginPage.invalidCredentials"),
        });
      } else if (responseError.response?.status === 403) {
        toast({
          variant: "destructive",
          title: t("loginPage.loginError"),
          description: t("loginPage.loginNotAllowed"),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("loginPage.loginError"),
          description: t("loginPage.tryAgain"),
        });
      }
    }
  });

  if (token && isTokenValid(token)) {
    return <Navigate to={`/${role_mapping[roles![0]]}`} replace />;
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      {codeInputOpen ? (
        <CodeInput roles={roles} />
      ) : (
        <Form {...form}>
          <form
            onSubmit={onSubmit}
            className="border-1 bg-white rounded-md border-black p-7 w-1/4 flex flex-col shadow-2xl relative"
          >
            <div className="flex justify-center">
              <h1 className="w-fit text-3xl font-bold">
                {t("logoPlaceholder")}
              </h1>
              <DropdownMenu>
                <DropdownMenuTrigger className="w-fit self-end absolute right-2 top-1">
                  <Button variant="ghost">
                    {t("loginPage.changeLanguage")}
                  </Button>
                </DropdownMenuTrigger>
                <DropdownMenuContent>
                  <DropdownMenuItem
                    onClick={() => {
                      setLanguage("en");
                    }}
                  >
                    EN
                  </DropdownMenuItem>
                  <DropdownMenuItem
                    onClick={() => {
                      setLanguage("pl");
                    }}
                  >
                    PL
                  </DropdownMenuItem>
                </DropdownMenuContent>
              </DropdownMenu>
            </div>
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
      )}
    </div>
  );
};

export default LoginPage;
