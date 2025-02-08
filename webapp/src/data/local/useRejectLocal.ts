import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "../useAxiosPrivate";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

export const useRejectLocal = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.patch(`/locals/${id}/reject`);
      queryClient.invalidateQueries({ queryKey: ["unapprovedLocals"] });
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

  return { rejectLocal: mutateAsync };
};
