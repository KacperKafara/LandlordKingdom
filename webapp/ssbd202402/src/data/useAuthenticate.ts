import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { AxiosError } from "axios";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { AuthenticateResponse } from "@/types/AuthenticateResponse";

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
      if (
        error.response?.status === 401 ||
        error.response?.status === 404 ||
        error.response?.status === 400
      ) {
        toast({
          variant: "destructive",
          title: t("loginPage.loginError"),
          description: t("loginPage.invalidCredentials"),
        });
      } else if (error.response?.status === 403) {
        const msg = error.response.data as string;
        if (msg.toLowerCase().includes("inactive")) {
          toast({
            variant: "destructive",
            title: t("error.baseTitle"),
            description: t("loginPage.inactiveAccount"),
          });
        } else {
          toast({
            variant: "destructive",
            title: t("loginPage.loginError"),
            description: t("loginPage.loginNotAllowed"),
          });
        }
      } else {
        toast({
          variant: "destructive",
          title: t("loginPage.loginError"),
          description: t("loginPage.tryAgain"),
        });
      }
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
    onError: () => {
      toast({
        variant: "destructive",
        title: t("loginPage.tokenError.title"),
        description: t("loginPage.tokenError.description"),
      });
    },
  });
  return { verifyCode: mutateAsync, isSuccess, isPending };
};
