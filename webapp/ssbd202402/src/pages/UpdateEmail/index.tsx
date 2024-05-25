import { FC } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useForm, SubmitHandler } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { useTranslation } from "react-i18next";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";

import { Input } from "@/components/ui/input";
import LoadingButton from "@/components/LoadingButton";
import { useChangeEmailAddress } from "@/data/useUpdateEmailAddress";
const updateEmailFormSchema = (t: TFunction) =>
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

type updateEmailFormValues = z.infer<ReturnType<typeof updateEmailFormSchema>>;

const UpdateEmailPage: FC = () => {
  const { token } = useParams<{ token: string }>();
  const { t } = useTranslation();
  const navigate = useNavigate();
  const { changeEmailAddress, isPending } = useChangeEmailAddress();
  const form = useForm<updateEmailFormValues>({
    resolver: zodResolver(updateEmailFormSchema(t)),
    values: {
      password: "",
      confirmPassword: "",
    },
  });

  const handleUserSubmit: SubmitHandler<updateEmailFormValues> = async (
    data: updateEmailFormValues
  ) => {
    await changeEmailAddress(
      {
        token: token ? token : "",
        password: data.password,
      },
      {
        onSuccess: () => {
          navigate("/login");
        },
      }
    );
  };

  return (
    <>
      <div className="flex h-screen flex-col items-center justify-center">
        <Form {...form}>
          <form
            className="border-1 relative flex w-1/4 flex-col rounded-md p-7 shadow-2xl"
            onSubmit={form.handleSubmit(handleUserSubmit)}
          >
            <h1 className="mb-10 text-center text-3xl">
              {t("updateEmailPage.updateEmailTitle")}
            </h1>
            <FormField
              control={form.control}
              name="password"
              render={({ field }) => (
                <FormItem className="my-3">
                  <FormLabel>{t("updateEmailPage.password")} </FormLabel>
                  <FormControl>
                    <Input type="password" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="confirmPassword"
              render={({ field }) => (
                <FormItem className="my-3">
                  <FormLabel>{t("updateEmailPage.confirmPassword")} </FormLabel>
                  <FormControl>
                    <Input type="password" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <LoadingButton
              type="submit"
              text={t("updateEmailPage.updateEmailButton")}
              isLoading={isPending}
            />
          </form>
        </Form>
      </div>
    </>
  );
};

export default UpdateEmailPage;
