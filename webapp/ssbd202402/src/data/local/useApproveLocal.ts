import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "../useAxiosPrivate";
import { useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";

export const useApproveLocal = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.patch(`/locals/${id}/approve`);
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { approveLocal: mutateAsync };
};
