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
import { DropdownMenu } from "@radix-ui/react-dropdown-menu";
import {
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { useLanguageStore } from "@/i18n/languageStore";
import { Loader2 } from "lucide-react";
import { useOAuthUrl } from "@/data/useOAuthUrl";
import { FaGoogle } from "react-icons/fa";

const getLoginSchema = (t: TFunction) =>
  z.object({
    login: z.string().min(1, t("loginPage.loginRequired")),
    password: z.string().min(1, t("loginPage.passwordRequired")),
  });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const Login2FaPage: FC = () => {
  const { t } = useTranslation();
  const { setToken, token, roles } = useUserStore();
  const { authenticate, isPending } = useAuthenticate();
  const { setLanguage } = useLanguageStore();
  const [login, setLogin] = useState<string>();

  const [codeInputOpen, setCodeInputOpen] = useState(false);
  const { oAuthUrl } = useOAuthUrl();
  const navigate = useNavigate();
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
    try {
      await authenticate({ login, password, language: i18next.language });
      setCodeInputOpen(true);
      setLogin(login);
    } catch (_) {}
  });

  if (token && isTokenValid(token)) {
    return <Navigate to={`/${role_mapping[roles![0]]}`} replace />;
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      {codeInputOpen ? (
        <CodeInput
          login={login || ""}
          roles={roles}
          setToken={setToken}
          setCodeInputOpen={setCodeInputOpen}
          resetForm={form.reset}
        />
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
            <Button type="submit" disabled={isPending}>
              {isPending && <Loader2 className="mr-2 h-4 w-4 animate-spin" />}
              {t("loginPage.loginButton")}
            </Button>
                     <Button
            variant="secondary"
            type="button"
              window.location.href = oAuthUrl?.url || "";
            onClick={() => {
            }}
          >
            <FaGoogle /> Login with google
          </Button>
            <Button variant="link" asChild className="w-fit self-center">
              <NavLink to={"/register"}>{t("loginPage.register")}</NavLink>
            </Button>
          </form>
        </Form>
      )}
 
    </div>
  );
};

export default Login2FaPage;
