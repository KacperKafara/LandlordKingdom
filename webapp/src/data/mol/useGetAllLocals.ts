import { AllLocals } from "@/types/mol/Locals";
import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { ErrorCode } from "@/@types/errorCode";

interface AllLocalsRequest {
  pageNumber: number;
  pageSize: number;
  state: string;
  ownerLogin: string;
}

interface AllLocalsResponse {
  locals: AllLocals[];
  totalPages: number;
}

export const useGetAllLocals = (request: AllLocalsRequest) => {
  const { api } = useAxiosPrivate();

  return useQuery({
    queryKey: [
      "allLocals",
      request.pageNumber,
      request.pageSize,
      request.state,
      request.ownerLogin,
    ],
    queryFn: async () => {
      try {
        const response = await api.get<AllLocalsResponse>("/locals", {
          params: {
            page: request.pageNumber,
            size: request.pageSize,
            state: request.state,
            ownerLogin: request.ownerLogin,
          },
        });
        return response.data;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("error.baseTitle"),
          description: t(
            `errors.${(axiosError.response!.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(axiosError);
      }
    },
  });
};
