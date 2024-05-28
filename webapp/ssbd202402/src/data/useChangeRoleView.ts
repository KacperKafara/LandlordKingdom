import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";

type RoleChangeInformation = {
  role: string;
};

export const useChangeRoleView = () => {
  const { api } = useAxiosPrivate();

  const { mutateAsync } = useMutation({
    mutationFn: async (data: RoleChangeInformation) => {
      await api.post("/me/role-view-changed", data);
    },
  });

  return { roleChanged: mutateAsync };
};
