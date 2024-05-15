import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "./useAxiosPrivate";

type ChangePasswordType = {
  oldPassword: string;
  newPassword: string;
};

export const useChangeUserPassword = () => {
  const { api } = useAxiosPrivate();
  const { mutateAsync } = useMutation({
    mutationFn: async (data: ChangePasswordType) => {
      const response = await api.post("/me/change-password", data);
      return response.status;
    },
  });

  return { changePassword: mutateAsync };
};
