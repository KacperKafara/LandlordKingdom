import { FC } from "react";
import { useTranslation } from "react-i18next";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { useForm, SubmitHandler } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import {
  Form,
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
} from "@/components/ui/form";
import { useAddLocal } from "@/data/mol/useAddLocal.ts";
import { TFunction } from "i18next";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { InputWithText } from "@/components/InputWithText";
import LoadingButton from "@/components/LoadingButton";

const addLocalSchema = (t: TFunction) =>
  z.object({
    name: z
      .string()
      .min(1, { message: t("addLocalPage.wrong.name") })
      .max(200, { message: t("addLocalPage.wrong.name") }),
    description: z
      .string()
      .min(1, { message: t("addLocalPage.wrong.description") })
      .max(5000, { message: t("addLocalPage.wrong.description") }),
    size: z.number().min(1, { message: t("addLocalPage.wrong.size") }),
    address: z.object({
      number: z
        .string()
        .min(1, { message: t("addLocalPage.wrong.number") })
        .max(10, { message: t("addLocalPage.wrong.number") }),
      street: z
        .string()
        .min(1, { message: t("addLocalPage.wrong.street") })
        .max(100, { message: t("addLocalPage.wrong.street") }),
      city: z
        .string()
        .min(1, { message: t("addLocalPage.wrong.city") })
        .max(100, { message: t("addLocalPage.wrong.city") }),
      zipCode: z
        .string()
        .regex(/^\d{2}-\d{3}$/, { message: t("addLocalPage.wrong.zip") }),
      country: z
        .string()
        .min(1, { message: t("addLocalPage.wrong.country") })
        .max(100, { message: t("addLocalPage.wrong.country") }),
    }),
    marginFee: z.number().min(0, t("addLocalPage.wrong.marginFee")),
    rentalFee: z.number().min(0, t("addLocalPage.wrong.rentalFee")),
  });

type AddLocalFormData = z.infer<ReturnType<typeof addLocalSchema>>;

const AddLocalForm: FC = () => {
  const { t } = useTranslation();
  const { addLocal, isPending } = useAddLocal();
  const form = useForm<AddLocalFormData>({
    resolver: zodResolver(addLocalSchema(t)),
    values: {
      name: "",
      description: "",
      size: 0,
      address: {
        number: "",
        street: "",
        city: "",
        zipCode: "",
        country: "",
      },
      marginFee: 0,
      rentalFee: 0,
    },
  });

  const breadcrumbs = useBreadcrumbs([
    { title: t("ownerLocals.title"), path: "/owner" },
    { title: t("ownerLocals.locals"), path: "/owner/locals" },
    { title: t("addLocalPage.title"), path: "/owner/locals/add" },
  ]);

  const onSubmit: SubmitHandler<AddLocalFormData> = async (data) => {
    await addLocal(data, {
      onSuccess: () => {
        form.reset();
      },
    });
  };

  return (
    <div className="flex flex-col gap-2">
      {breadcrumbs}
      <Card className="mt-3 flex justify-center">
        <CardHeader>
          <CardTitle>{t("addLocalPage.title")}</CardTitle>
        </CardHeader>
      </Card>
      <Card className="relative mt-3">
        <CardContent>
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(onSubmit)}
              className="grid grid-cols-1 gap-4 md:grid-cols-2"
            >
              <FormField
                control={form.control}
                name="name"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.name")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <FormField
                control={form.control}
                name="size"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.size")}</FormLabel>
                    <FormControl>
                      <InputWithText
                        {...field}
                        type="number"
                        onChange={(event) => {
                          const value = parseFloat(event.target.value);
                          field.onChange(value);
                        }}
                        rightText="m²"
                      />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="address.number"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.number")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="address.street"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.street")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="address.city"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.city")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="address.zipCode"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.zip")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <FormField
                control={form.control}
                name="address.country"
                render={({ field }) => (
                  <FormItem>
                    <FormLabel>{t("addLocalPage.country")}</FormLabel>
                    <FormControl>
                      <Input {...field} />
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <div className="grid grid-cols-2 gap-4">
                <FormField
                  control={form.control}
                  name="marginFee"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t("addLocalPage.marginFee")}</FormLabel>
                      <FormControl>
                        <InputWithText
                          {...field}
                          type="number"
                          onChange={(event) => {
                            const value = parseFloat(event.target.value);
                            field.onChange(value);
                          }}
                          rightText={t("currency")}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="rentalFee"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t("addLocalPage.rentalFee")}</FormLabel>
                      <FormControl>
                        <InputWithText
                          {...field}
                          type="number"
                          onChange={(event) => {
                            const value = parseFloat(event.target.value);
                            field.onChange(value);
                          }}
                          rightText={t("currency")}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <div className="col-span-1 md:col-span-2">
                <FormField
                  control={form.control}
                  name="description"
                  render={({ field }) => (
                    <FormItem>
                      <FormLabel>{t("addLocalPage.description")}</FormLabel>
                      <FormControl>
                        <textarea
                          {...field}
                          rows={4}
                          className="flex w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50"
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>

              <div className="col-span-1 md:col-span-2">
                <LoadingButton
                  isLoading={isPending}
                  type="submit"
                  className="w-full"
                  text={t("addLocalPage.formSubmit")}
                />
              </div>
            </form>
          </Form>
        </CardContent>
      </Card>
    </div>
  );
};

export default AddLocalForm;
