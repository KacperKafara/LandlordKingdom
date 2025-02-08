import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { toast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { t } from "i18next";

export const useRejectApplication = () => {
  const { api } = useAxiosPrivate();
  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.delete(`/locals/applications/${id}`);
    },
    onSuccess: () => {
      toast({
        variant: "success",
        title: t("localApplications.rejectSuccess"),
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

  return { rejectApplication: mutateAsync };
};
