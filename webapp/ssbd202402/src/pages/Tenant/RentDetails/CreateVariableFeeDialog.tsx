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
import { Input } from "@/components/ui/input";
import { useCreateVariableFee } from "@/data/rent/useCreateVariableFee";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import React, { FC } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { z } from "zod";

const getCreateVariableFeeSchema = (t: TFunction) =>
  z.object({
    rentId: z.string(),
    amount: z
      .number()
      .min(0, t("createVariableFeeDialog.amountMustBePositive")),
  });

type CreateVariableFeeForm = z.infer<
  ReturnType<typeof getCreateVariableFeeSchema>
>;

type CreateVariableFeeDialogProps = {
  rentId: string;
};

const CreateVariableFeeDialogComponent: FC<CreateVariableFeeDialogProps> = ({
  rentId,
}) => {
  const { t } = useTranslation();
  const form = useForm<CreateVariableFeeForm>({
    resolver: zodResolver(getCreateVariableFeeSchema(t)),
    values: {
      rentId,
      amount: 0,
    },
  });
  const { createVariableFee } = useCreateVariableFee();

  const handleSubmit = form.handleSubmit(async (values) => {
    await createVariableFee(values);
  });

  return (
    <Dialog>
      <DialogTrigger asChild>
        <Button>{t("createVariableFeeDialog.title")}</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>{t("createVariableFeeDialog.title")}</DialogTitle>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={handleSubmit} className="flex flex-col gap-3">
            <FormField
              control={form.control}
              name="amount"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("createVariableFeeDialog.amount")}</FormLabel>
                  <FormControl>
                    <Input
                      {...field}
                      type="number"
                      onChange={(e) => field.onChange(+e.target.value)}
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <Button type="submit" className="self-end">
              {t("createVariableFeeDialog.title")}
            </Button>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

const CreateVariableFeeDialog = React.memo(CreateVariableFeeDialogComponent);

export default CreateVariableFeeDialog;
