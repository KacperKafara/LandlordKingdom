import { useQuery } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";
import { AxiosError } from "axios";
import { useToast } from "@/components/ui/use-toast";
import { t } from "i18next";
import { RoleRequest } from "@/types/roleRequest/RoleRequest";
import { ErrorCode } from "@/@types/errorCode";

export const fetchRoleRequests = () => {
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
