import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { ApplicationsForTenant } from "@/types/tenant/applicationsForTenant";

export const useGetOwnApplications = () => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();

  return useQuery({
    queryKey: ["tenantOwnApplications"],
    queryFn: async () => {
      try {
        const response =
          await api.get<ApplicationsForTenant[]>("/me/applications");
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
