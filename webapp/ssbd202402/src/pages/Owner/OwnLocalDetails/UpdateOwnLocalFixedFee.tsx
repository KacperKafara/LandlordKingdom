import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useMutateOwnLocalFixedFee } from "@/data/local/useMutateOwnLocalFixedFee";
import {
  AlertDialog,
  AlertDialogTrigger,
  AlertDialogContent,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogCancel,
  AlertDialogAction,
} from "@/components/ui/alert-dialog";
import { z } from "zod";
import { TFunction } from "i18next";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
  Form,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { toast } from "@/components/ui/use-toast";

const UpdateFixedFeeSchema = (t: TFunction) =>
  z.object({
    rentalFee: z
      .string()
      .min(1, t("updateOwnLocalFixedFeeForm.rentalFeeNotEmpty"))
      .regex(
        /^\d+(\.\d{1,2})?$/,
        t("updateOwnLocalFixedFeeForm.rentalFeeNotValid")
      )
      .refine((value) => parseFloat(value) <= 10000, {
        message: t("updateOwnLocalFixedFeeForm.rentalFeeTooLarge"),
      }),
    marginFee: z
      .string()
      .min(1, t("updateOwnLocalFixedFeeForm.marginFeeNotEmpty"))
      .regex(
        /^\d+(\.\d{1,2})?$/,
        t("updateOwnLocalFixedFeeForm.marginFeeNotValid")
      )
      .refine((value) => parseFloat(value) <= 10000, {
        message: t("updateOwnLocalFixedFeeForm.marginFeeTooLarge"),
      }),
  });
type UpdateOwnLocalFixedFee = z.infer<ReturnType<typeof UpdateFixedFeeSchema>>;

interface Props {
  id: string;
  initialRentalFee: number;
  initialMarginFee: number;
  etag: string;
}

const UpdateOwnLocalFixedFee: FC<Props> = ({
  id,
  initialRentalFee,
  initialMarginFee,
  etag,
}) => {
  const { t } = useTranslation();
  const { mutateAsync } = useMutateOwnLocalFixedFee();
  const form = useForm<UpdateOwnLocalFixedFee>({
    resolver: zodResolver(UpdateFixedFeeSchema(t)),
    defaultValues: {
      rentalFee: initialRentalFee.toString(),
      marginFee: initialMarginFee.toString(),
    },
    mode: "onBlur",
  });

  const updateFixedFeeClick = form.handleSubmit(
    async (data: UpdateOwnLocalFixedFee) => {
      let etagValue = etag;
      if (!etag) {
        toast({
          variant: "destructive",
          title: t("updateLocalPage.errorTitle"),
        });
        return;
      }
      etagValue = etagValue.substring(1, etagValue.length - 1);

      const formattedData = {
        id,
        rentalFee: parseFloat(data.rentalFee),
        marginFee: parseFloat(data.marginFee),
        ifMatch: etagValue,
      };
      await mutateAsync(formattedData);
    }
  );

  return (
    <Form {...form}>
      <form
        onSubmit={updateFixedFeeClick}
        className="flex w-3/4 flex-col gap-3"
      >
        <FormField
          control={form.control}
          name="rentalFee"
          render={({ field }) => (
            <FormItem>
              <FormLabel>
                {t("updateOwnLocalFixedFeeForm.rentalFeeInput")}{" "}
              </FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <FormField
          control={form.control}
          name="marginFee"
          render={({ field }) => (
            <FormItem>
              <FormLabel>
                {t("updateOwnLocalFixedFeeForm.marginFeeInput")}{" "}
              </FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <AlertDialog>
          <AlertDialogTrigger asChild>
            <Button>{t("updateOwnLocalFixedFeeForm.updateFixedFee")}</Button>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>
                {t("updateOwnLocalFixedFeeForm.updateFixedFeeTitle")}
              </AlertDialogTitle>
              <AlertDialogDescription>
                {t("updateOwnLocalFixedFeeForm.updateFixedFeeDescription")}
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
              <AlertDialogAction asChild>
                <button type="submit" onClick={() => updateFixedFeeClick()}>
                  {t("confirm")}
                </button>
              </AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </form>
    </Form>
  );
};

export default UpdateOwnLocalFixedFee;
