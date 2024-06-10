import { useQuery } from "@tanstack/react-query";
import { useTranslation } from "react-i18next";
import { api } from "../api";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { ErrorCode } from "@/@types/errorCode";


type RentFixedFeesRequest = {
    id: string;
    pageNumber: number;
    pageSize: number;
    startDate: string;
    endDate: string;
}

type RentFixedFee = {
    rentFixedFees: {
        date: string;
        rentalFee: number;
        marginFee: number;
    }[];
    totalPages: number;
}


export const useRentFixedFees = (request: RentFixedFeesRequest) => {
    const { t } = useTranslation();

    return useQuery({
      queryKey: ["rentFixedFees", request.id, request.pageNumber, request.pageSize, request.startDate, request.endDate],
      queryFn: async () => {
        try {
          const query = `?pageNum=${request.pageNumber}&pageSize=${request.pageSize}`;
          const result = await api.post<RentFixedFee>(`/me/rents/${request.id}/fixed-fees` + query, {
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
  