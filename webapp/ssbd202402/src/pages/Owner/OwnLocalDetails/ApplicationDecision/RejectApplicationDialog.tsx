import ConfirmDialog from "@/components/ConfirmDialog";
import { useTranslation } from "react-i18next";
import { LocalApplications as ApplicationType } from "@/types/local/LocalApplications";
import { FC } from "react";
import { useRejectApplication } from "@/data/application/useRejectApplication";
import { useQueryClient } from "@tanstack/react-query";

interface Props {
  application: ApplicationType;
}

const RejectApplicationDialog: FC<Props> = ({ application }) => {
  const { rejectApplication } = useRejectApplication();
  const queryClient = useQueryClient();
  const { t } = useTranslation();

  const handleRejectApplication = async (id: string) => {
    await rejectApplication(id);
    queryClient.invalidateQueries({ queryKey: ["localApplications"] });
  };

  return (
    <>
      <ConfirmDialog
        buttonText={t("localApplications.reject")}
        variant="destructive"
        dialogTitle={t("localApplications.rejectTitle")}
        dialogDescription={t("localApplications.rejectDescription")}
        confirmAction={async () =>
          await handleRejectApplication(application.id)
        }
      />
    </>
  );
};

export default RejectApplicationDialog;
