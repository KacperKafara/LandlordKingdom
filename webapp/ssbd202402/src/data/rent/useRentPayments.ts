import { useQuery } from "@tanstack/react-query";
import {AxiosError} from "axios";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";
import {ErrorCode} from "@/@types/errorCode.ts";
import { api } from "../api";

type RentPaymentsRequest = {
    id: string;
    pageNumber: number;
    pageSize: number;
    startDate: string;
    endDate: string;
}

type RentPayment = {
    rentPayments: {
        date: string;
        amount: number;
    }[];
    totalPages: number;
}

export const useRentPayments = (request: RentPaymentsRequest) => {

  return useQuery({
    queryKey: ["rentPayments", request.id, request.pageNumber, request.pageSize, request.startDate, request.endDate],
    queryFn: async () => {
      try {
        const query = `?pageNum=${request.pageNumber}&pageSize=${request.pageSize}`;
        const result = await api.post<RentPayment>(`/me/rents/${request.id}/payments` + query, {
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
