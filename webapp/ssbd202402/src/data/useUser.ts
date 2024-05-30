import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { DetailedUserResponse } from "@/types/user/DetailedUserResponseType";
import {AxiosError, AxiosInstance} from "axios";
import useAxiosPrivate from "./useAxiosPrivate";
import { UserUpdateRequestType } from "@/types/user/UserUpdateRequestType";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { ErrorCode } from "@/@types/errorCode";

interface UpdateUserRequest {
  id: string;
  request: UserUpdateRequestType;
  etag: string;
}

export const fetchUser = async (id: string, api: AxiosInstance) => {
  try {
    const response = await api.get<DetailedUserResponse>(`/users/${id}`);
    return response;
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
};

export const updateUser = async (
  data: UpdateUserRequest,
  api: AxiosInstance
) => {
  await api.put(`/users/${data.id}`, data.request, {
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
      toast({
        variant: "destructive",
        title: t("updateDataForm.error"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
};
