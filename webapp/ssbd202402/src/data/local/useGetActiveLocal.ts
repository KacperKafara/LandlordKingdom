import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { ActiveLocalDetails } from "@/types/local/ActiveLocalDetails";
import { ErrorCode } from "@/@types/errorCode";

export const useGetActiveLocal = (id: string) => {
  const { api } = useAxiosPrivate();

  const { data } = useQuery({
    queryKey: ["activeLocalDetails"],
    queryFn: async () => {
      try {
        const response = await api.get<ActiveLocalDetails>(
          `/locals/active/${id}`
        );
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

  return { local: data };
};
