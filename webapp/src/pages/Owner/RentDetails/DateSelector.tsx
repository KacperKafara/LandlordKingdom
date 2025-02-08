import { Button } from "@/components/ui/button";
import { Calendar } from "@/components/ui/calendar";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { useLanguageStore } from "@/i18n/languageStore";
import { cn } from "@/lib/utils";
import { format } from "date-fns";
import { CalendarIcon } from "lucide-react";
import { FC } from "react";
import { SelectSingleEventHandler } from "react-day-picker";
import { pl, enUS } from "date-fns/locale";
import { useTranslation } from "react-i18next";

type DateSelectorProps = {
  date: Date;
  setDate: SelectSingleEventHandler;
};

const DateSelector: FC<DateSelectorProps> = ({ date, setDate }) => {
  const { language } = useLanguageStore();
  const { t } = useTranslation();
  return (
    <Popover>
      <PopoverTrigger asChild>
        <Button
          variant={"outline"}
          className={cn(
            "w-[280px] justify-start text-left font-normal",
            !date && "text-muted-foreground"
          )}
        >
          <CalendarIcon className="mr-2 h-4 w-4" />
          {date ? (
            format(date, "PPP", { locale: language === "pl" ? pl : enUS })
          ) : (
            <span>{t("ownerRentDetails.pickDate")}</span>
          )}
        </Button>
      </PopoverTrigger>
      <PopoverContent className="w-auto p-0">
        <Calendar
          locale={language === "pl" ? pl : enUS}
          mode="single"
          selected={date}
          onSelect={setDate}
          initialFocus
          defaultMonth={date}
        />
      </PopoverContent>
    </Popover>
  );
};

export default DateSelector;
