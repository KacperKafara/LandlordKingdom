import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

import { OwnLocalDetails } from "@/types/owner/OwnLocalDetails";

export const useGetOwnLocalDetails = (id: string) => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();

  return useQuery({
    queryKey: ["ownLocalDetails", id],
    queryFn: async () => {
      try {
        const response = await api.get<OwnLocalDetails>(`/me/locals/${id}`);
        return response.data;
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
