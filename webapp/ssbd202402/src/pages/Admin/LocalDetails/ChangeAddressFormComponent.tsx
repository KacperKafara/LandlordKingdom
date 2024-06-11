import ConfirmDialog from "@/components/ConfirmDialog";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useEditLocalAddress } from "@/data/local/useEditLocalAddress";
import { useGetLocalDetailsForAdmin } from "@/data/local/useGetLocalDetailsForAdmin";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction, t } from "i18next";
import { FC } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const getAddressSchema = (t: TFunction) =>
  z.object({
    city: z
      .string()
      .min(1, t("changeAddressForm.cityValidation"))
      .max(100, t("changeAddressForm.cityValidation")),
    country: z
      .string()
      .min(1, t("changeAddressForm.countryValidation"))
      .max(100, t("changeAddressForm.countryValidation")),
    street: z
      .string()
      .min(1, t("changeAddressForm.streetValidation"))
      .max(100, t("changeAddressForm.streetValidation")),
    number: z
      .string()
      .min(1, t("changeAddressForm.numberValidation"))
      .max(10, t("changeAddressForm.numberValidation")),
    zipCode: z
      .string()
      .regex(/^\d{2}-\d{3}$/, t("changeAddressForm.zipCodeValidation")),
  });

type AddressSchema = z.infer<ReturnType<typeof getAddressSchema>>;

interface ChangeAddressFormComponentProps {
  localId: string;
}

const ChangeAddressFormComponent: FC<ChangeAddressFormComponentProps> = ({
  localId,
}) => {
  const { data } = useGetLocalDetailsForAdmin(localId);
  const { editLocalAddress } = useEditLocalAddress();

  const form = useForm<AddressSchema>({
    resolver: zodResolver(getAddressSchema(t)),
    defaultValues: {
      country: data?.data.address.country || "",
      city: data?.data.address.city || "",
      street: data?.data.address.street || "",
      number: data?.data.address.number || "",
      zipCode: data?.data.address.zipCode || "",
    },
  });

  const handleFormSubmit = form.handleSubmit((data) => {
    editLocalAddress({ id: localId, address: data });
  });

  return (
    <Card className="relative">
      <CardHeader>
        <CardTitle className="text-center">
          {t("localDetails.changeAddress")}
        </CardTitle>
      </CardHeader>
      <CardContent className="flex justify-center">
        <Form {...form}>
          <form
            onSubmit={handleFormSubmit}
            className="flex w-3/4 flex-col gap-3"
          >
            <FormField
              control={form.control}
              name="country"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("changeAddressForm.country")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="city"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("changeAddressForm.city")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="street"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("changeAddressForm.street")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="number"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("changeAddressForm.number")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="zipCode"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("changeAddressForm.zipCode")}</FormLabel>
                  <FormControl>
                    <Input {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <ConfirmDialog
              className="mt-5"
              buttonText={t("common.update")}
              dialogTitle={t("common.confirmDialogTitle")}
              dialogDescription={t(
                "changeAddressForm.confirmDialogDescription"
              )}
              confirmAction={() => handleFormSubmit()}
            />
          </form>
        </Form>
      </CardContent>
      <RefreshQueryButton
        className="absolute right-1 top-1"
        queryKeys={["localDetailsForAdmin"]}
      />
    </Card>
  );
};

export default ChangeAddressFormComponent;
