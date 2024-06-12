import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

import { LocalDetailsForAdmin } from "@/types/admin/LocalDetailsForAdmin";

export const useGetLocalDetailsForAdmin = (id: string) => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();

  return useQuery({
    queryKey: ["localDetailsForAdmin", id],
    queryFn: async () => {
      try {
        const response = await api.get<LocalDetailsForAdmin>(`/locals/${id}`);
        return response;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("localDetails.error"),
          description: t(
            `errors.${(axiosError.response!.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(error);
      }
    },
  });
};
