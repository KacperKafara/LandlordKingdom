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
import i18next, { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { NavLink, useNavigate } from "react-router-dom";
import { z } from "zod";
import { Toaster } from "@/components/ui/toaster";
import LanguageSelector from "@/components/LanguageSelector";
import LoadingButton from "@/components/LoadingButton";
import { useRegisterUser } from "@/data/useRegisterUser";

const getRegistrationSchema = (t: TFunction) =>
  z
    .object({
      firstName: z
        .string()
        .min(
          1,
          t("validation.minLength") +
            " " +
            1 +
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
      lastName: z
        .string()
        .min(
          1,
          t("validation.minLength") +
            " " +
            1 +
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
      email: z
        .string()
        .email(t("registerPage.emailRequired"))
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

type RegistrationSchema = z.infer<ReturnType<typeof getRegistrationSchema>>;

const RegisterPage: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { registerUserAsync, isPending } = useRegisterUser();

  const form = useForm<RegistrationSchema>({
    resolver: zodResolver(getRegistrationSchema(t)),
    values: {
      firstName: "",
      lastName: "",
      email: "",
      login: "",
      password: "",
      confirmPassword: "",
    },
  });

  const onSubmit = form.handleSubmit(async (values) => {
    await registerUserAsync({
      firstName: values.firstName,
      lastName: values.lastName,
      email: values.email,
      login: values.login,
      password: values.password,
      language: i18next.language,
    });

    navigate("/register-success");
  });

  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-1 relative flex w-6/12 flex-col rounded-md border-black bg-white p-7 shadow-2xl"
        >
          <LanguageSelector />
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center pb-7 pt-3 text-2xl">
            {t("registerPage.registerHeader")}
          </h2>
          <div className="grid grid-cols-2 items-stretch gap-3">
            <div className="grid grid-rows-3 gap-2">
              <FormField
                control={form.control}
                name="firstName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("registerPage.firstName")}</FormLabel>
                    <FormControl>
                      <Input {...field} autoComplete="firstname" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="lastName"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("registerPage.lastName")}</FormLabel>
                    <FormControl>
                      <Input {...field} autoComplete="lastname" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="email"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("registerPage.email")}</FormLabel>
                    <FormControl>
                      <Input {...field} autoComplete="email" type="email" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
            <div className="grid grid-rows-3 gap-2">
              <FormField
                control={form.control}
                name="login"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("registerPage.login")}</FormLabel>
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
                    <FormLabel>{t("registerPage.password")}</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
                        type="password"
                        autoComplete="new-password"
                      />
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
                    <FormLabel>{t("registerPage.confirmPassword")}</FormLabel>
                    <FormControl>
                      <Input
                        {...field}
                        type="password"
                        autoComplete="new-password"
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
            </div>
          </div>
          <LoadingButton
            type="submit"
            className="mt-5 w-5/12 self-center p-2"
            isLoading={isPending}
            text={t("registerPage.registerButton")}
          />
          <Button variant="link" asChild className="w-fit self-center">
            <NavLink to={"/login"}>{t("loginPage.loginButton")}</NavLink>
          </Button>
        </form>
      </Form>
      <Toaster />
    </div>
  );
};

export default RegisterPage;
