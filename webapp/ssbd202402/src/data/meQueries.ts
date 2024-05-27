import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { UserResponse } from "@/types/user/UserResponseType.ts";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType.ts";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import { useLanguageStore } from "@/i18n/languageStore";
import useAxiosPrivate from "./useAxiosPrivate";
import { AxiosInstance } from "axios";

const getMeData = async (
  changeLanguage: (lang: string) => void,
  api: AxiosInstance
) => {
  const response = await api.get<UserResponse>("/me");
  changeLanguage(response.data.language);
  return response;
};

interface UserUpdateRequest {
  request: UserUpdateRequestType;
  etag: string;
}

const putMeData = async (data: UserUpdateRequest, api: AxiosInstance) => {
  await api.put("/me", data.request, {
    headers: {
      "If-Match": data.etag,
    },
  });
};

export const useMeQuery = () => {
  const { setLanguage } = useLanguageStore();
  const { api } = useAxiosPrivate();
  return useQuery({
    queryKey: ["meData"],
    queryFn: () => getMeData(setLanguage, api),
  });
};

export const useMeMutation = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  const { api } = useAxiosPrivate();
  return useMutation({
    mutationFn: (data: UserUpdateRequest) => putMeData(data, api),
    onSettled: async (_, error) => {
      if (error) {
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: t(
            `errors.${(error.response?.data as ErrorCode).exceptionCode}`
          ),
        });
      } else {
        await queryClient.invalidateQueries({ queryKey: ["meData"] });
        toast({
          title: t("userDataPage.success"),
        });
      }
    },
  });
};
