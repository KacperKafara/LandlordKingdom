import { FC, useState } from "react";
import {
  AlertDialog,
  AlertDialogAction,
  AlertDialogCancel,
  AlertDialogContent,
  AlertDialogDescription,
  AlertDialogFooter,
  AlertDialogHeader,
  AlertDialogTitle,
  AlertDialogTrigger,
} from "@/components/ui/alert-dialog";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";
import { LocalApplications as ApplicationType } from "@/types/local/LocalApplications";
import { useQueryClient } from "@tanstack/react-query";
import DateSelector from "../../RentDetails/DateSelector";
import { useAcceptApplication } from "@/data/application/useAcceptApplication";
import { format } from "date-fns";
import { toast } from "@/components/ui/use-toast";

interface Props {
  application: ApplicationType;
}

const AcceptApplicationDialog: FC<Props> = ({ application }) => {
  const { t } = useTranslation();
  const { acceptApplication } = useAcceptApplication();
  const [endDate, setEndDate] = useState<Date | undefined>(
    new Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000)
  );
  const queryClient = useQueryClient();

  const handleAcceptApplication = async (id: string) => {
    if (endDate === undefined) {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(`localApplications.endDateNeeded`),
      });
    }

    await acceptApplication({
      id,
      data: { endDate: format(endDate!.toString(), "yyyy-MM-dd") },
    });
    queryClient.invalidateQueries({ queryKey: ["localApplications"] });
  };

  return (
    <>
      <AlertDialog>
        <AlertDialogTrigger asChild>
          <Button>{t("localApplications.accept")}</Button>
        </AlertDialogTrigger>
        <AlertDialogContent>
          <AlertDialogHeader>
            <AlertDialogTitle>
              {t("localApplications.acceptTitle")}
            </AlertDialogTitle>
            <AlertDialogDescription>
              {t("localApplications.acceptDescription")}
              <div className="mt-2">
                <DateSelector date={endDate!} setDate={setEndDate} />
              </div>
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter>
            <AlertDialogCancel>{t("cancel")}</AlertDialogCancel>
            <AlertDialogAction asChild>
              <Button onClick={() => handleAcceptApplication(application.id)}>
                {t("confirm")}
              </Button>
            </AlertDialogAction>
          </AlertDialogFooter>
          <div className="text-justify">
            {t("localApplications.acceptFooter")}
          </div>
        </AlertDialogContent>
      </AlertDialog>
    </>
  );
};

export default AcceptApplicationDialog;
