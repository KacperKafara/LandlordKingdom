import { FC, useState } from "react";
import { Button } from "@/components/ui/button";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Calendar } from "@/components/ui/calendar";
import { Calendar as CalendarIcon } from "lucide-react";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { useLanguageStore } from "@/i18n/languageStore";
import { pl, enUS } from "date-fns/locale";
import { useTranslation } from "react-i18next";
import { useMutateEndDate } from "@/data/rent/useMutateEndDate";
import { TFunction } from "i18next";

const FormSchema = (t: TFunction) =>
  z.object({
    newDate: z.date({
      required_error: t("changeEndDate.newDateRequired"),
    }),
  });

type FormSchemaType = z.infer<ReturnType<typeof FormSchema>>;

type Props = {
  startDate: string;
  endDate: string;
  id: string;
};

export const ChangeEndDate: FC<Props> = ({ startDate, endDate, id }) => {
  const { language } = useLanguageStore();
  const { t } = useTranslation();
  const { mutate, isPending } = useMutateEndDate();
  const [isOpen, setOpen] = useState(false);
  const form = useForm<FormSchemaType>({
    resolver: zodResolver(FormSchema(t)),
  });

  function onSubmit(data: FormSchemaType) {
    mutate(
      { id, newDate: format(data.newDate, "yyyy-MM-dd") },
      {
        onSuccess: () => {
          setOpen(false);
        },
      }
    );
  }
  return (
    <Dialog open={isOpen} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button className="flex-1" variant="default">
          {t("changeEndDate.buttonText")}
        </Button>
      </DialogTrigger>
      <DialogContent className="sm:max-w-[425px]">
        <DialogHeader>
          <DialogTitle>{t("changeEndDate.dialogTitle")}</DialogTitle>
          <DialogDescription>
            <p>{t("changeEndDate.dialogDescription")} </p>
          </DialogDescription>
        </DialogHeader>
        <Form {...form}>
          <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-8">
            <FormField
              control={form.control}
              name="newDate"
              render={({ field }) => (
                <FormItem className="flex flex-col">
                  <FormLabel>{t("changeEndDate.formLabel")}</FormLabel>
                  <Popover>
                    <PopoverTrigger asChild>
                      <FormControl>
                        <Button
                          variant={"outline"}
                          className={cn(
                            "pl-3 text-left font-normal",
                            !field.value && "text-muted-foreground"
                          )}
                        >
                          {field.value ? (
                            format(field.value, "PPP", {
                              locale: language === "pl" ? pl : enUS,
                            })
                          ) : (
                            <span>{t("changeEndDate.spanText")}</span>
                          )}
                          <CalendarIcon className="ml-auto h-4 w-4 opacity-50" />
                        </Button>
                      </FormControl>
                    </PopoverTrigger>
                    <PopoverContent className="w-auto p-0" align="end">
                      <Calendar
                        locale={language === "pl" ? pl : enUS}
                        mode="single"
                        selected={field.value}
                        onSelect={field.onChange}
                        disabled={(date) =>
                          date.getDay() !== 0 ||
                          date <= new Date(startDate) ||
                          date < new Date() ||
                          date.setHours(0, 0, 0, 0) ===
                            new Date(endDate).setHours(0, 0, 0, 0)
                        }
                        initialFocus
                        weekStartsOn={1}
                      />
                    </PopoverContent>
                  </Popover>
                  <FormDescription>
                    <p>{t("changeEndDate.formDescription")}</p>
                  </FormDescription>
                  <FormMessage />
                </FormItem>
              )}
            />

            <DialogFooter>
              <Button disabled={isPending} type="submit">
                {t("changeEndDate.saveChanges")}
              </Button>
            </DialogFooter>
          </form>
        </Form>
      </DialogContent>
    </Dialog>
  );
};
