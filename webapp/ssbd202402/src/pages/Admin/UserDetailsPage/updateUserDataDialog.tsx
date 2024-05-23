import { Button } from "@/components/ui/button";
import { useUpdateUserMutation } from "@/data/useUser";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType";
import { FC, useEffect } from "react";
import { z } from "zod";
import { TFunction } from "i18next";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
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
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import {
  AlertDialog,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
} from "@/components/ui/alert-dialog";

interface props {
  id: string;
  userData: UserUpdateRequestType;
  etag: string;
  setOpenUpdateUserDataDialog: (value: boolean) => void;
  openUpdateUserDataDialog: boolean;
}

const updateUserSchema = (t: TFunction) =>
  z.object({
    firstName: z.string().max(50).min(1, t("userDataPage.firstNameNotEmpty")),
    lastName: z.string().max(50).min(1, t("userDataPage.lastNameNotEmpty")),
    language: z.string().regex(/^(en|pl)$/),
  });

type UpdateUserSchema = z.infer<ReturnType<typeof updateUserSchema>>;

const UpdateUserDataDialog: FC<props> = ({
  id,
  userData,
  etag,
  setOpenUpdateUserDataDialog,
  openUpdateUserDataDialog,
}) => {
  const putMutation = useUpdateUserMutation();
  const { t } = useTranslation();

  const form = useForm<UpdateUserSchema>({
    resolver: zodResolver(updateUserSchema(t)),
    values: {
      firstName: userData.firstName,
      lastName: userData.lastName,
      language: userData.language,
    },
  });

  useEffect(() => {
    form.reset({
      firstName: userData.firstName,
      lastName: userData.lastName,
      language: userData.language,
    });
  }, [form, userData.firstName, userData.lastName, userData.language]);

  const onSubmit = form.handleSubmit((request) => {
    etag = etag.substring(1, etag.length - 1);
    putMutation.mutate({ id, request, etag });
    setOpenUpdateUserDataDialog(false);
  });

  return (
    <AlertDialog open={openUpdateUserDataDialog}>
      <AlertDialogContent>
        <AlertDialogHeader>
          <AlertDialogTitle>{t("updateDataForm.title")}</AlertDialogTitle>
        </AlertDialogHeader>
        <Form {...form}>
          <form onSubmit={onSubmit}>
            <FormField
              control={form.control}
              name="firstName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("updateDataForm.firstName")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
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
                  <FormLabel>{t("updateDataForm.firstName")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="language"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("updateDataForm.language")}</FormLabel>
                  <div>
                    <FormControl>
                      <Select
                        onValueChange={field.onChange}
                        defaultValue={field.value}
                      >
                        <SelectTrigger className="">
                          <SelectValue placeholder="Theme" />
                        </SelectTrigger>
                        <SelectContent>
                          <SelectItem value="pl">Polski</SelectItem>
                          <SelectItem value="en">English</SelectItem>
                        </SelectContent>
                      </Select>
                    </FormControl>
                  </div>
                  <FormMessage />
                </FormItem>
              )}
            />
            <div className="flex justify-between">
              <Button className="mt-5 self-end" type="submit">
                {t("updateDataForm.updateButton")}
              </Button>
              <Button
                className="mt-5"
                variant="outline"
                onClick={(e) => {
                  e.preventDefault();
                  setOpenUpdateUserDataDialog(false);
                }}
              >
                {t("cancel")}
              </Button>
            </div>
          </form>
        </Form>
      </AlertDialogContent>
    </AlertDialog>
  );
};

export default UpdateUserDataDialog;
