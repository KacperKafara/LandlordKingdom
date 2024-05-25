import { FC } from "react";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { useResetOtherUserEmailAddress } from "@/data/useUpdateEmailAddress.ts";
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
import {
  FormField,
  FormItem,
  FormLabel,
  FormControl,
  FormMessage,
  Form,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { TFunction } from "i18next";



const UpdateEmailAddressSchema = (t: TFunction) => 
  z.object({
    email: z.string().min(3, t("userDataPage.emailNotEmpty")).max(50, t("userDataPage.emailTooLong")).email( t("userDataPage.emailNotValid")),
  });


type UpdateEmailAddressType = z.infer<ReturnType<typeof UpdateEmailAddressSchema>>;

type UpdateEmailAddressProps = {
  id: string
}

const UpdateUserEmailAddress: FC<UpdateEmailAddressProps> = ({id}) => {
  const { t } = useTranslation();
  const { updateEmail } = useResetOtherUserEmailAddress();
  const form = useForm<UpdateEmailAddressType>({
    resolver: zodResolver(UpdateEmailAddressSchema(t)),
    values: {
      email: "",
    },
    mode: "onBlur",
  });

  const updateEmailAddressClick  = form.handleSubmit(
    async (data: UpdateEmailAddressType) => {
    await updateEmail({id: id, email: data.email});
    form.reset();
});


  return (
  
     <Form {...form}>
      <form onSubmit={(updateEmailAddressClick)} className="flex w-3/4 flex-col gap-3">
        <FormField
          control={form.control}
          name="email"
          render={({ field }) => (
            <FormItem>
              <FormLabel>{t("userDetailsPage.email")} </FormLabel>
              <FormControl>
                <Input {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />
        <AlertDialog>
        <AlertDialogTrigger asChild>
          <Button >{t("userDetailsPage.updateEmailAddress")}</Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>
              {t("userDetailsPage.updateEmailAddressTitle")}
            </AlertDialogTitle>
            <AlertDialogDescription>
              {t("userDetailsPage.updateEmailAddressDescription")}
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
            <AlertDialogAction asChild>
              <Button type="submit" onClick={() => updateEmailAddressClick()}>
                {t("confirm")}
              </Button>
            </AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
        </form>
      </Form>
    
  );
};

export default UpdateUserEmailAddress;
