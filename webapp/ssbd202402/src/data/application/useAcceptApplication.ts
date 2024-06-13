import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

interface AcceptApplicationData {
  endDate: string;
}

export const useAcceptApplication = () => {
  const { api } = useAxiosPrivate();
  const { mutateAsync, isPending } = useMutation({
    mutationFn: async ({
      id,
      data,
    }: {
      id: string;
      data: AcceptApplicationData;
    }) => {
      await api.post(`/locals/applications/${id}`, data);
    },
    onSuccess: () => {
      toast({
        variant: "success",
        title: t("localApplications.acceptSuccess"),
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

  return { acceptApplication: mutateAsync, isPending };
};
