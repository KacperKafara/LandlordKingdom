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
import { TFunction } from "i18next";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";

const getLoginSchema = (t: TFunction) =>
    z.object({
        login: z.string().min(1, t("loginPage.loginRequired")),
        password: z.string().min(1, t("loginPage.passwordRequired")),
    });

type LoginSchema = z.infer<ReturnType<typeof getLoginSchema>>;

const LoginPage: FC = () => {
    const { t } = useTranslation();
    const { setToken, token, roles } = useUserStore();
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
        console.log(roles);
        if (roles == undefined) {
            return navigate("/login")
        } else {
            switch (roles[0]) {
                case "ADMINISTRATOR":
                    navigate("/admin/test");
                    break;
                case "TENANT":
                    navigate("/tenant/test");
                    break;
                case "OWNER":
                    navigate("/owner/test");
                    break;
                default:
                    navigate("/login");
            }
        }

    });

        if (token && roles) {
            switch (roles[0]) {
                case "ADMINISTRATOR":
                    return <Navigate to={"/admin/test"} />;
                case "TENANT":
                    return <Navigate to={"/tenant/test"} />;
                case "OWNER":
                    return <Navigate to={"/owner/test"} />;
                default:
                    return <Navigate to={"/login"} />;
            }
        }

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
            </div>
        );
    };

    export default LoginPage;