import { useQuery } from "@tanstack/react-query";
import { api } from "./api";
import {AxiosError} from "axios";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";
import {ErrorCode} from "@/@types/errorCode.ts";

type OAuth2UrlResponse = {
  url: string;
};

export const useOAuthUrl = () => {
  const { data } = useQuery({
    queryKey: ["oauthUrl"],
    queryFn: async () => {
      try {
        const response = await api.get<OAuth2UrlResponse>("/auth/oauth2/url");
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

  return { oAuthUrl: data };
};
