import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { AxiosError } from "axios";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AuthenticateResponse } from "@/types/AuthenticateResponse";
import { ErrorCode } from "@/@types/errorCode";

type AuthenticateRequest = {
  login: string;
  password: string;
  language: string;
};

type CodeVerificationRequest = {
  login: string;
  token: string;
};

export const useAuthenticate = () => {
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync, isSuccess, isPending } = useMutation({
    mutationFn: async (data: AuthenticateRequest) => {
      await api.post("/auth/signin-2fa", data);
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("loginPage.loginError"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });

  return { authenticate: mutateAsync, isSuccess, isPending };
};

export const useVerifyCode = () => {
  const { toast } = useToast();
  const { t } = useTranslation();

  const { mutateAsync, isSuccess, isPending } = useMutation({
    mutationFn: async (data: CodeVerificationRequest) => {
      const response = await api.post<AuthenticateResponse>(
        "/auth/verify-2fa",
        data
      );
      return response.data;
    },
    onError: (error: AxiosError) => {
      toast({
        variant: "destructive",
        title: t("loginPage.tokenError.title"),
        description: t(
          `errors.${(error.response?.data as ErrorCode).exceptionCode}`
        ),
      });
    },
  });
  return { verifyCode: mutateAsync, isSuccess, isPending };
};
