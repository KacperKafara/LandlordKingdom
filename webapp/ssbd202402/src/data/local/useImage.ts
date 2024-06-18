import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode";

type UploadImageRequest = {
  id: string;
  image: FormData;
};

export const useUploadImage = () => {
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (data: UploadImageRequest) => {
      await api.post(
        `/images/upload/${data.id}`,
        {
          file: data.image.get("file"),
        },
        {
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }
      );
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["localImages"] });
      toast({
        title: t("uploadImage.uploadImageSuccess"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("uploadImage.uploadImageError"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};

export const useGetLocalImages = (id: string) => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();
  const { toast } = useToast();

  return useQuery({
    queryKey: ["localImages", id],
    queryFn: async () => {
      try {
        const response = await api.get<Array<string>>(`/images/local/${id}`);
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
};

export const useDeleteImage = () => {
  const { api } = useAxiosPrivate();
  const { t } = useTranslation();
  const { toast } = useToast();
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: async (id: string) => {
      await api.delete(`/images/${id}`);
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["localImages"] });
      toast({
        title: t("uploadImage.deleteSuccessTitle"),
        description: t("uploadImage.deleteSuccessDescription"),
      });
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("uploadImage.deleteErrorTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};
