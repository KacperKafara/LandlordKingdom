import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { ErrorCode } from "@/@types/errorCode";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";

export const useCreateApplication = () => {
  const { api } = useAxiosPrivate();
  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.post(`/locals/${id}/applications`);
    },
    onSuccess: () => {
      toast({
        variant: "success",
        title: t("activeLocalDetails.applicationCreated"),
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

  return { createApplication: mutateAsync };
};
