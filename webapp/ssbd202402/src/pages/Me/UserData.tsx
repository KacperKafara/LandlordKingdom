import { Button } from "@/components/ui/button";
import {
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
  Form,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "@/components/ui/select";
import { useMeMutation, useMeQuery } from "@/data/meQueries";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { z } from "zod";

const updateUserSchema = (t: TFunction) =>
  z.object({
    firstName: z.string().max(50).min(1, t("userDataPage.firstNameNotEmpty")),
    lastName: z.string().max(50).min(1, t("userDataPage.lastNameNotEmpty")),
    language: z.string().regex(/^(en|pl)$/),
  });

type UpdateUserSchema = z.infer<ReturnType<typeof updateUserSchema>>;

const UserData: FC = () => {
  const { t } = useTranslation();
  const { data } = useMeQuery();
  const putMutation = useMeMutation();
  const form = useForm<UpdateUserSchema>({
    resolver: zodResolver(updateUserSchema(t)),
    values: {
      firstName: data?.data.firstName || "",
      lastName: data?.data.lastName || "",
      language: data?.data.language || "",
    },
  });

  const handleUserSubmit = form.handleSubmit((request) => {
    let etag: string = data?.headers.etag;
    etag = etag.substring(1, etag.length - 1);
    putMutation.mutate({ request, etag });
  });

  return (
    <Form {...form}>
      <form onSubmit={handleUserSubmit} className="flex w-3/4 flex-col gap-3">
        <FormField
          control={form.control}
          name="firstName"
          render={({ field }) => (
            <FormItem>
              <FormLabel>{t("userDataPage.firstName")} </FormLabel>
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
              <FormLabel>{t("userDataPage.lastName")} </FormLabel>
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
              <FormLabel>{t("userDataPage.language")}</FormLabel>
              <div>
                <FormControl>
                  <Select
                    onValueChange={field.onChange}
                    defaultValue={field.value}
                  >
                    <SelectTrigger className="">
                      <SelectValue />
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
        <Button className="mt-5" type="submit">
          {t("userDataPage.updateUserData")}
        </Button>
      </form>
    </Form>
  );
};

export default UserData;
