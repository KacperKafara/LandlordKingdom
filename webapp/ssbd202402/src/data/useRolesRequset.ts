import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import { AxiosError } from "axios";
import { useToast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { RoleRequest } from "@/types/roleRequest/RoleRequest";
import { ErrorCode } from "@/@types/errorCode";

const useGetRoleRequests = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();

  const { data, isLoading } = useQuery({
    queryKey: ["roleRequests"],
    queryFn: async () => {
      try {
        const result = await api.get("/roles");
        return result.data;
      } catch (error) {
        const axiosError = error as AxiosError;
        toast({
          variant: "destructive",
          title: t("error.baseTitle"),
          description: t(
            `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
          ),
        });
        return Promise.reject(error);
      }
    },
  });

  return { roleRequests: data as RoleRequest[], isLoading };
};

const useAcceptRoleRequest = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.post(`/roles/${id}`);
    },
    onError: (error: AxiosError) => {
      queryClient.invalidateQueries({ queryKey: ["roleRequests"] });
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["roleRequests"] });
      toast({
        variant: "success",
        title: t("notApprovedActionsPage.roleRequestApproveSuccess"),
      });
    },
  });

  return { acceptRoleRequest: mutateAsync };
};

const useRejectRoleRequest = () => {
  const { api } = useAxiosPrivate();
  const { toast } = useToast();
  const queryClient = useQueryClient();

  const { mutateAsync } = useMutation({
    mutationFn: async (id: string) => {
      await api.delete(`/roles/${id}`);
    },
    onError: (error: AxiosError) => {
      queryClient.invalidateQueries({ queryKey: ["roleRequests"] });
      toast({
        variant: "destructive",
        title: t("error.baseTitle"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["roleRequests"] });
      toast({
        variant: "success",
        title: t("notApprovedActionsPage.roleRequestRejectSuccess"),
      });
    },
  });

  return { rejectRoleRequest: mutateAsync };
};

export { useGetRoleRequests, useAcceptRoleRequest, useRejectRoleRequest };
