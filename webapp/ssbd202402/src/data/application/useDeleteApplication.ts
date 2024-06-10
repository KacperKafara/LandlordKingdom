import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

export const useDeleteApplication = () => {
  const { api } = useAxiosPrivate();
  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.delete(`/locals/${id}/applications`);
    },
    onSuccess: () => {
      toast({
        variant: "success",
        title: t("tenantApplications.aplicationDeleted"),
      });
    },
    onError: (error: AxiosError<ErrorCode>) => {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(`errors.${error.response!.data.exceptionCode}`),
      });
    },
  });

  return { deleteApplication: mutateAsync };
};
