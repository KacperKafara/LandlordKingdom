import { InputWithText } from "@/components/InputWithText";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { useCreatePayment } from "@/data/rent/useCreatePayment";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import React, { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { z } from "zod";

const getCreatePaymentSchema = (t: TFunction) =>
  z.object({
    rentId: z.string(),
    amount: z
      .number()
      .min(0.01, t("createPaymentDialog.amountMustBePositive"))
      .max(
        10000,
        t("createPaymentDialog.amountMustBeLessThanOrEqualToTenThousand")
      )
      .refine(
        (value) => Number.isInteger(value * 100),
        t("createPaymentDialog.amountMustHaveAtMostTwoFractionalDigits")
      ),
  });

type CreatePaymentForm = z.infer<ReturnType<typeof getCreatePaymentSchema>>;

type CreatePaymentDialogProps = {
  rentId: string;
};

const CreatePaymentDialogComponent: FC<CreatePaymentDialogProps> = ({
  rentId,
}) => {
  const { t } = useTranslation();
  const form = useForm<CreatePaymentForm>({
    resolver: zodResolver(getCreatePaymentSchema(t)),
    values: {
      rentId,
      amount: 0,
    },
  });
  const { createPayment } = useCreatePayment();

  const handleSubmit = form.handleSubmit(async (values) => {
    await createPayment(values);
  });

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>{t("createPaymentDialog.title")}</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{t("createPaymentDialog.title")}</DialogTitle>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={handleSubmit} className="flex flex-col gap-3">
            <FormField
              control={form.control}
              name="amount"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("createPaymentDialog.amount")}</FormLabel>
                  <FormControl>
                    <InputWithText
                      {...field}
                      type="number"
                      onChange={(e) => field.onChange(+e.target.value)}
                      rightText={t("currency")}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="self-end">
              {t("createPaymentDialog.title")}
            </Button>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

const CreatePaymentDialog = React.memo(CreatePaymentDialogComponent);

export default CreatePaymentDialog;
