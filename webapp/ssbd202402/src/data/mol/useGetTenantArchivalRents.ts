import { TenantOwnRents } from "@/types/tenant/rentForTenant";
import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { toast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";



export const useGetTenantArchivalRents = () => {
    const { api } = useAxiosPrivate();
    const { t } = useTranslation();

    return useQuery({
        queryKey: ["tenantArchivalRents"],
        queryFn: async () => {
            try {
                const response = await api.get<TenantOwnRents[]>("/me/rents/archival");    
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

}