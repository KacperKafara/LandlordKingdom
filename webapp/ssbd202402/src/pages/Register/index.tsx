import { Button } from "@/components/ui/button";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { NavLink, useNavigate } from "react-router-dom";
import { z } from "zod";

const getRegistrationSchema = (t: TFunction) =>
  z.object({
    firstName: z.string().min(1, t("registerPage.firstNameRequired")),
    lastName: z.string().min(1, t("registerPage.lastNameRequired")),
    email: z.string().email(t("registerPage.emailRequired")),
    login: z.string().min(1, t("registerPage.loginRequired")),
    password: z.string().min(8, t("registerPage.passwordRequired")),
    confirmPassword: z.string().min(8, t("registerPage.passwordMatch")),
  })
  .refine(data => data.password === data.confirmPassword, {
    message: t("registerPage.passwordMatch"),
    path: ["confirmPassword"],
  });

type RegistrationSchema = z.infer<ReturnType<typeof getRegistrationSchema>>;

const RegisterPage: FC = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
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
    console.log(values);
    navigate("/login");
  });

  return (
    <div className="flex items-center justify-center min-h-screen">
      <Form {...form}>
        <form onSubmit={onSubmit} className="border-2 rounded-md border-black p-7 w-6/12 flex flex-col">
          <h1 className="self-center text-3xl font-bold">
            {t("logoPlaceholder")}
          </h1>
          <h2 className="self-center text-2xl pb-7 pt-3">
            {t("registerPage.registerHeader")}
          </h2>
          <div className="grid grid-cols-2 gap-3 items-stretch">
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
                    <Input {...field} />
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
                  <FormLabel>{t("registerPage.confirmPassword")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="password" />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            </div>
          </div>
          <Button type="submit" className="mt-3 p-2 w-5/12 self-center">{t("registerPage.registerButton")}</Button>
          <Button variant="link" asChild className="w-fit self-center">
            <NavLink to={"/login"}>{t("loginPage.loginButton")}</NavLink>
          </Button>
        </form>
      </Form>
    </div>
  );
};

export default RegisterPage;
