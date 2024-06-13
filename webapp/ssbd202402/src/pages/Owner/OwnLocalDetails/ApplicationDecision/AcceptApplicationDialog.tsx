import { FC } from "react";
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { LocalApplications as ApplicationType } from "@/types/local/LocalApplications";
import { useQueryClient } from "@tanstack/react-query";
import { useAcceptApplication } from "@/data/application/useAcceptApplication";
import { format } from "date-fns";
import { Calendar } from "@/components/ui/calendar";
import { useLanguageStore } from "@/i18n/languageStore";
import { pl, enUS } from "date-fns/locale";
import { TFunction } from "i18next";
import { z } from "zod";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { cn } from "@/lib/utils";
import { CalendarIcon } from "lucide-react";

const FormSchema = (t: TFunction) =>
  z.object({
    endDate: z.date({
      required_error: t("localApplications.endDateNeeded"),
    }),
  });

type FormSchemaType = z.infer<ReturnType<typeof FormSchema>>;

interface Props {
  application: ApplicationType;
}

const AcceptApplicationDialog: FC<Props> = ({ application }) => {
  const { language } = useLanguageStore();
  const { t } = useTranslation();
  const { acceptApplication, isPending } = useAcceptApplication();
  const queryClient = useQueryClient();

  const form = useForm<FormSchemaType>({
    resolver: zodResolver(FormSchema(t)),
  });

  const handleAcceptApplication = async (data: FormSchemaType) => {
    await acceptApplication({
      id: application.id,
      data: { endDate: format(data.endDate, "yyyy-MM-dd") },
    });
    queryClient.invalidateQueries({ queryKey: ["localApplications"] });
  };

  return (
    <>
      <Dialog>
        <DialogTrigger asChild>
          <Button>{t("localApplications.accept")}</Button>
        </DialogTrigger>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>{t("localApplications.acceptTitle")}</DialogTitle>
            <DialogDescription>
              {t("localApplications.acceptDescription")}
            </DialogDescription>
          </DialogHeader>
          <Form {...form}>
            <form
              onSubmit={form.handleSubmit(handleAcceptApplication)}
              className="space-y-8"
            >
              <FormField
                control={form.control}
                name="endDate"
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
                            date.getDay() !== 0 || date < new Date()
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
                  {t("confirm")}
                </Button>
              </DialogFooter>
            </form>
          </Form>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default AcceptApplicationDialog;
