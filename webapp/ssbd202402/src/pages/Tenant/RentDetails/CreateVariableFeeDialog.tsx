import { InputWithText } from "@/components/InputWithText";
import LoadingButton from "@/components/LoadingButton";
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
import { useCreateVariableFee } from "@/data/rent/useCreateVariableFee";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";
import React, { FC, useState } from "react";
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
  const [isOpen, setOpen] = useState(false);
  const form = useForm<CreateVariableFeeForm>({
    resolver: zodResolver(getCreateVariableFeeSchema(t)),
    values: {
      rentId,
      amount: 0,
    },
  });
  const { createVariableFee, isPending } = useCreateVariableFee();

  const handleSubmit = form.handleSubmit(async (values) => {
    await createVariableFee(values, {
      onSuccess: () => {
        setOpen(false);
      },
    });
  });

  return (
    <Dialog open={isOpen} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button onClick={() => setOpen(true)}>
          {t("createVariableFeeDialog.title")}
        </Button>
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
            <LoadingButton
              className="self-end"
              type="submit"
              text={t("createVariableFeeDialog.title")}
              isLoading={isPending}
            />
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

const CreateVariableFeeDialog = React.memo(CreateVariableFeeDialogComponent);

export default CreateVariableFeeDialog;
