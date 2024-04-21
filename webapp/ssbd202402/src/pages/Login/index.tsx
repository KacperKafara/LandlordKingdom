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

const loginSchema = z.object({
  login: z.string(),
  password: z.string(),
});

type LoginSchema = z.infer<typeof loginSchema>;

const LoginPage: FC = () => {
  const { t } = useTranslation();
  const form = useForm<LoginSchema>({
    resolver: zodResolver(loginSchema),
    values: {
      login: "",
      password: "",
    },
  });

  const onSubmit = form.handleSubmit((values) => {
    console.log(values);
  });
  return (
    <div className="flex items-center justify-center min-h-screen">
      <Form {...form}>
        <form
          onSubmit={onSubmit}
          className="border-2 rounded-md border-black p-7 w-96 flex flex-col"
        >
          <h1 className="self-center text-3xl font-bold pb-7">
            {t("logoPlaceholder")}
          </h1>
          <FormField
            control={form.control}
            name="login"
            render={({ field }) => (
              <FormItem>
                <FormLabel>{t("loginPage.login")}</FormLabel>
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
                <FormLabel>{t("loginPage.password")}</FormLabel>
                <FormControl>
                  <Input {...field} type="password" />
                </FormControl>
                <FormMessage />
              </FormItem>
            )}
          />
          <div className="text-sm text-slate-600">
            {t("loginPage.forgotPassword")}
          </div>
          <Button type="submit" className="self-end">
            {t("loginPage.loginButton")}
          </Button>
        </form>
      </Form>
    </div>
  );
};

export default LoginPage;
