import { useQuery } from "@tanstack/react-query";
import { api } from "../api";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";




type RentVariableFeesRequest = {
    id: string;
    pageNumber: number;
    pageSize: number;
    startDate: string;
    endDate: string;
}

type RentVariableFee = {
    rentVariableFees: {
        date: string;
        amount: number;
    }[];
    totalPages: number;
}



export const useRentVariableFees = (request: RentVariableFeesRequest) => {
    const { t } = useTranslation();

    return useQuery({
      queryKey: ["rentVariableFees", request.id, request.pageNumber, request.pageSize, request.startDate, request.endDate],
      queryFn: async () => {
        try {
          const query = `?pageNum=${request.pageNumber}&pageSize=${request.pageSize}`;
          const result = await api.post<RentVariableFee>(`/me/rents/${request.id}/variable-fees` + query, {
              startDate: request.startDate,
              endDate: request.endDate,            
          });
          return result.data;
        } catch (error) {
          const axiosError = error as AxiosError;
          toast({
            variant: "destructive",
            title: t("userDataPage.error"),
            description: t(
                `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
            ),
          });
          return Promise.reject(error);
        }
      },
    });
  };
  