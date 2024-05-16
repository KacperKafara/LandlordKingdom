import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { DetailedUserResponse } from "@/types/user/DetailedUserResponseType";
import { AxiosError, AxiosInstance } from "axios";
import useAxiosPrivate from "./useAxiosPrivate";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";

interface UpdateUserRequest {
  id: string;
  request: UserUpdateRequestType;
  etag: string;
}

export const fetchUser = async (id: string, api: AxiosInstance) => {
  const response = await api.get<DetailedUserResponse>(`/users/${id}`);
  return response;
};

export const updateUser = async (
  data: UpdateUserRequest,
  api: AxiosInstance
) => {
  await api.put(`/users/${data.id}/update-data`, data.request, {
    headers: {
      "If-Match": data.etag,
    },
  });
};

export const useGetUserQuery = (id: string) => {
  const { api } = useAxiosPrivate();
  return useQuery({
    queryKey: ["user", id],
    queryFn: () => (id ? fetchUser(id, api) : Promise.resolve(null)),
  });
};

export const useUpdateUserMutation = () => {
  const { api } = useAxiosPrivate();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: (data: UpdateUserRequest) => updateUser(data, api),
    onSuccess: async () => {
      await queryClient.invalidateQueries({ queryKey: ["user"] });
      toast({
        title: t("updateDataForm.success"),
      });
    },
    onError: (error: AxiosError) => {
      if (error.response?.status === 412) {
        toast({
          variant: "destructive",
          title: t("updateDataForm.error"),
          description: t("updateDataForm.precondinationFailed"),
        });
      } else {
        toast({
          variant: "destructive",
          title: t("updateDataForm.error"),
        });
      }
    },
  });
};
