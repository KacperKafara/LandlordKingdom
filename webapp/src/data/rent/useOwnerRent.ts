import useAxiosPrivate from "../useAxiosPrivate";
import { useQuery } from "@tanstack/react-query";
import { toast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";
import { RentDetailsForOwner } from "@/types/owner/RentDetailsForOwner";



  

export const useOwnerRent = (id: string) => {
    const { api } = useAxiosPrivate();
    const { t } = useTranslation();

    return useQuery({
        queryKey: ["rentDetailsForOwner", id],
        queryFn: async () => {
            try {
                const response = await api.get<RentDetailsForOwner>(`/me/owner/rents/${id}`);    
                return response.data;
            } catch (error) {
                const axiosError = error as AxiosError;
                toast({
                  variant: "destructive",
                  title: t("ownerRentDetails.error"),
                  description: t(
                      `errors.${(axiosError.response!.data as ErrorCode).exceptionCode}`
                  ),
                });
                return Promise.reject(error);
            }
            
           
        },
    });

}