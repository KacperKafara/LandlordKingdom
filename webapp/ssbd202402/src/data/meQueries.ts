import { api } from "@/data/api.ts";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { UserResponse } from "@/types/user/UserResponseType.ts";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType.ts";
import { useToast } from "@/components/ui/use-toast.ts";
import { useTranslation } from "react-i18next";
import useLanguage from "@/i18n/languageHook";

const getMeData = async (changeLanguage: (lang: string) => void) => {
  const response = await api.get<UserResponse>("/me");
  changeLanguage(response.data.language);
  return response;
};

interface UserUpdateRequest {
  request: UserUpdateRequestType;
  etag: string;
}

const putMeData = async (data: UserUpdateRequest) => {
  await api.put("/me", data.request, {
    headers: {
      "If-Match": data.etag,
    },
  });
};

export const useMeQuery = () => {
  const { changeLanguage } = useLanguage();
  return useQuery({
    queryKey: ["meData"],
    queryFn: () => getMeData(changeLanguage),
  });
};

export const useMeMutation = () => {
  const queryClient = useQueryClient();
  const { toast } = useToast();
  const { t } = useTranslation();
  return useMutation({
    mutationFn: putMeData,
    onSettled: async (_, error) => {
      if (error) {
        toast({
          variant: "destructive",
          title: t("userDataPage.error"),
          description: error.message,
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
