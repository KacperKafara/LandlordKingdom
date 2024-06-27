import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { OwnerArchivalRent } from "@/types/mol/OwnerCurrentRent";

interface OwnRentsRequest {
  pageNumber: number;
  pageSize: number;
}


type OwnerArchivalRentsPage = {
  rents: OwnerArchivalRent[];
  pages: number;
}


export const useGetOwnerArchivalRents = (request: OwnRentsRequest) => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();

  return useQuery({
    queryKey: ["ownerArchivalRents", request.pageNumber, request.pageSize],
    queryFn: async () => {
      try {
        const response = await api.get<OwnerArchivalRentsPage>("/me/owner/rents/archival", {
          params: {
            page: request.pageNumber,
            size: request.pageSize,
          },
        });
        return response.data;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: t(
            `errors.${(axiosError.response!.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(error);
      }
    },
  });
};
