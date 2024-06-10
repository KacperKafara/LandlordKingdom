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
import { FC } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";

const createVariableFeeSchema = z.object({
  rentId: z.string(),
  amount: z.number(),
});

type CreateVariableFeeDialogProps = {
  rentId: string;
};

type CreateVariableFeeForm = z.infer<typeof createVariableFeeSchema>;

const CreateVariableFeeDialog: FC<CreateVariableFeeDialogProps> = ({
  rentId,
}) => {
  const form = useForm<CreateVariableFeeForm>({
    resolver: zodResolver(createVariableFeeSchema),
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
        <Button>Add Variable fee</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Add Variable fee</DialogTitle>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={handleSubmit}>
            <FormField
              control={form.control}
              name="amount"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>Amount</FormLabel>
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
            <Button type="submit">Add Variable fee</Button>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};

export default CreateVariableFeeDialog;
