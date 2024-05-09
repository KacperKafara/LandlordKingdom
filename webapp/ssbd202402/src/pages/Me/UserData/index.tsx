import { FC } from "react";
import { useMeQuery, useMeMutation } from "@/data/meQueries.ts";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { z } from "zod";
import { SubmitHandler, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@/components/ui/input.tsx";
import { cn } from "@/lib/utils.ts";
import { Button, buttonVariants } from "@/components/ui/button.tsx";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType.ts";
import { useTranslation } from "react-i18next";
import { TFunction } from "i18next";
import { Toaster } from "@/components/ui/toaster.tsx";
import { useChangeUserPassword } from "@/data/useChangeUserPassword";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";
import {
  AlertDialogFooter,
  AlertDialogHeader,
} from "@/components/ui/alert-dialog";
import { AlertDialogTrigger } from "@radix-ui/react-alert-dialog";

const userDataFormSchema = (t: TFunction) =>
  z.object({
    firstName: z.string().max(50).min(1, t("userDataPage.firstNameNotEmpty")),
    lastName: z.string().max(50).min(1, t("userDataPage.lastNameNotEmpty")),
    language: z.string().regex(/^(en|pl)$/),
  });

const passwordChangeSchema = (t: TFunction) =>
  z
    .object({
      oldPassword: z.string().min(8, t("registerPage.passwordRequired")),
      newPassword: z.string().min(8, t("registerPage.passwordRequired")),
      confirmPassword: z.string().min(8, t("registerPage.passwordMatch")),
    })
    .refine((data) => data.newPassword === data.confirmPassword, {
      message: t("registerPage.passwordMatch"),
      path: ["confirmPassword"],
    });

type userDataFormValues = z.infer<ReturnType<typeof userDataFormSchema>>;
type passwordChangeValues = z.infer<ReturnType<typeof passwordChangeSchema>>;

const UserDataPage: FC = () => {
  const { changePassword } = useChangeUserPassword();
  const { data } = useMeQuery();
  const putMutation = useMeMutation();
  const { t } = useTranslation();
  const form = useForm<userDataFormValues>({
    resolver: zodResolver(userDataFormSchema(t)),
    values: {
      firstName: data?.data.firstName || "",
      lastName: data?.data.lastName || "",
      language: data?.data.language || "",
    },
  });

  const passwordChangeForm = useForm<passwordChangeValues>({
    resolver: zodResolver(passwordChangeSchema(t)),
    values: {
      oldPassword: "",
      newPassword: "",
      confirmPassword: "",
    },
  });

  const handleUserSubmit: SubmitHandler<UserUpdateRequestType> = (request) => {
    let etag: string = data?.headers.etag;
    etag = etag.substring(1, etag.length - 1);
    console.log(data);
    console.log(etag);
    putMutation.mutate({ request, etag });
  };

  const handlePasswordChangeSubmit = passwordChangeForm.handleSubmit(
    async (values) => {
      try {
        await changePassword({
          oldPassword: values.oldPassword,
          newPassword: values.newPassword,
        });
        toast({
          title: t("changePasswordForm.success"),
        });
        passwordChangeForm.reset();
      } catch (error) {
        const errorResponse = error as AxiosError;
        if (errorResponse.response?.status === 404) {
          toast({
            variant: "destructive",
            title: t("changePasswordForm.errorTitle"),
            description: t("changePasswordForm.errorDescriptionNotFound"),
          });
        } else if (errorResponse.response?.status === 400) {
          toast({
            variant: "destructive",
            title: t("changePasswordForm.errorTitle"),
            description: t("changePasswordForm.errorDescriptionBadRequest"),
          });
        } else {
          toast({
            variant: "destructive",
            title: t("changePasswordForm.errorTitle"),
          });
        }
      }
    }
  );

  return (
    <>
      <div className="flex flex-col gap-20">
        <div>
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(handleUserSubmit)}
              className="flex flex-col gap-3"
            >
              <FormField
                control={form.control}
                name="firstName"
                render={({ field }) => (
                  <FormItem className="my-3">
                    <FormLabel>{t("userDataPage.firstName")} </FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    {/*<FormDescription> Description </FormDescription>*/}
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="lastName"
                render={({ field }) => (
                  <FormItem className="mb-3">
                    <FormLabel>{t("userDataPage.lastName")} </FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    {/*<FormDescription> Description </FormDescription>*/}
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="language"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("userDataPage.language")}</FormLabel>
                    <div>
                      <FormControl>
                        <select
                          className={cn(
                            buttonVariants({ variant: "outline" }),
                            "w-[200px] appearance-none font-normal"
                          )}
                          {...field}
                        >
                          <option value="pl">Polski</option>
                          <option value="en">English</option>
                        </select>
                      </FormControl>
                    </div>
                    {/*<FormDescription> Description </FormDescription>*/}
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Button className="mt-5" type="submit">
                Update
              </Button>
            </form>
          </Form>
        </div>
        <div>
          <Form {...passwordChangeForm}>
            <form
              onSubmit={handlePasswordChangeSubmit}
              className="flex flex-col gap-3"
            >
              <FormField
                control={passwordChangeForm.control}
                name="oldPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("changePasswordForm.oldPassword")}</FormLabel>
                    <FormControl>
                      <Input {...field} type="password" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={passwordChangeForm.control}
                name="newPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("changePasswordForm.newPassword")}</FormLabel>
                    <FormControl>
                      <Input {...field} type="password" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={passwordChangeForm.control}
                name="confirmPassword"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>
                      {t("changePasswordForm.confirmPassword")}
                    </FormLabel>
                    <FormControl>
                      <Input {...field} type="password" />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <AlertDialog>
                <AlertDialogTrigger asChild>
                  <Button>{t("changePasswordForm.submit")}</Button>
                </AlertDialogTrigger>
                <AlertDialogContent>
                  <AlertDialogHeader>
                    <AlertDialogTitle>
                      {t("changePasswordForm.alertDialogTitle")}
                    </AlertDialogTitle>
                    <AlertDialogDescription>
                      {t("changePasswordForm.alertDialogDescription")}
                    </AlertDialogDescription>
                  </AlertDialogHeader>
                  <AlertDialogFooter>
                    <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
                    <AlertDialogAction asChild>
                      <Button
                        type="submit"
                        onClick={() => handlePasswordChangeSubmit()}
                      >
                        {t("confirm")}
                      </Button>
                    </AlertDialogAction>
                  </AlertDialogFooter>
                </AlertDialogContent>
              </AlertDialog>
            </form>
          </Form>
        </div>
      </div>

      <Toaster />
    </>
  );
};

export default UserDataPage;
