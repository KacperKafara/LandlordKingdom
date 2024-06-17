import { useMutation, useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

type UploadImageRequest = {
  id: string;
  image: Int8Array;
};

export const useUploadImage = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();

  return useMutation({
    mutationFn: async (data: UploadImageRequest) => {
      await api.post(`/images/upload/${data.id}`, {
        file: data.image,
      }, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      });
    },
    onSuccess: () => {
      toast({
        title: t("changeFixedFee.successTitle"),
        description: t("changeFixedFee.successDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("changeFixedFee.errorTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};

// type LocalImageResponse = {
//     extension: string;
//     image: string;
// };

export const useGetLocalImages = (id: string) => {
    const { api } = useAxiosPrivate();
    const { t } = useTranslation();
    const { toast } = useToast();

    return useQuery({
        queryKey: ["localImages", id],
        queryFn: async () => {
            try {
                const response = await api.get(`/images/${id}`);    
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