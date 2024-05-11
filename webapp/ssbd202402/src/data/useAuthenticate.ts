import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

type AuthenticateRequest = {
  login: string;
  password: string;
  language: string;
};

type AuthenticateResponse = {
  token: string;
};

type CodeVerificationRequest = {
  token: string;
};

export const useAuthenticate = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (data: AuthenticateRequest) => {
      const response = await api.post("/auth/signin-2fa", data);
      return response.data;
    },
  });

  return { authenticate: mutateAsync };
};

export const useVerifyCode = () => {
  const { mutateAsync } = useMutation({
    mutationFn: async (data: CodeVerificationRequest) => {
      const response = await api.post<AuthenticateResponse>(
        "/auth/verify-2fa",
        data
      );
      return response.data;
    },
  });
  return { verifyCode: mutateAsync };
};
