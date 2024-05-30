import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import {AxiosError} from "axios";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";
import {ErrorCode} from "@/@types/errorCode.ts";

type UserFilterResponse = {
  login: string;
  email: string;
  firstName: string;
  lastName: string;
  role: string;
  blocked: boolean;
  verified: boolean;
  pageSize: number;
};

export const useUserFilter = () => {
  const { api } = useAxiosPrivate();
  const { data } = useQuery({
    queryKey: ["userFilter"],
    queryFn: async () => {
      try {
        const { data } = await api.get<UserFilterResponse>("/filter/user");
        return data;
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

  return { userFilter: data };
};
