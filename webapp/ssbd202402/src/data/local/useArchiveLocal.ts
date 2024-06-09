import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import useAxiosPrivate from "../useAxiosPrivate";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { AxiosError } from "axios";

export const useArchiveLocal = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.patch(`/locals/${id}/archive`);
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("localDetails.archiveError"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["localDetailsForAdmin"] });
      toast({
        variant: "success",
        title: t("localDetails.archiveSuccess"),
      });
    },
  });

  return { archiveLocal: mutateAsync };
};
