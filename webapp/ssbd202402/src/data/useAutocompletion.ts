import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import {AxiosError} from "axios";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";
import {ErrorCode} from "@/@types/errorCode.ts";

export const useAutocompletionQuery = (loginPattern: string) => {
  const { api } = useAxiosPrivate();

  const params = {
    query: loginPattern,
  };

  return useQuery({
    queryKey: ["autocompletion", loginPattern],
    queryFn: async () => {
      try {
        const response = await api.get<string[]>("/autocomplete/login", {
          params,
        });
        return response.data;
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
