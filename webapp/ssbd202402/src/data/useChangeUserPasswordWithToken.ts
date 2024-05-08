import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

type ChangePasswordType = {
  password: string;
  token: string;
};

export const useChangeUserPasswordWithToken = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (data: ChangePasswordType) => {
      const response = await api.post("/me/change-password-with-token", data);
      return response.status;
    },
  });

  return { changePassword: mutateAsync };
};
