import { useUserStore } from "@/store/userStore";
import { api } from "./api";
import { useEffect } from "react";
import { AxiosError } from "axios";
import { useNavigate } from "react-router-dom";
import { toast } from "@/components/ui/use-toast";
import { t } from "i18next";

const useAxiosPrivate = () => {
  const {
    token,
    refreshToken,
    setRefreshToken,
    clearRefreshToken,
    clearToken,
    setToken,
  } = useUserStore();

  const navigation = useNavigate();

  useEffect(() => {
    const requestInterceptor = api.interceptors.request.use(
      (config) => {
        console.log("requestInterceptor");
        const token = localStorage.getItem("token");
        if (token && !config.url?.includes("auth")) {
          config.headers.Authorization = `Bearer ${token}`;
        }

        return config;
      },
      (error) => {
        return Promise.reject(error);
      }
    );

    const responseInterceptor = api.interceptors.response.use(
      (response) => response,
      (error) => {
        const prevRequest = error.config;
        error = error as AxiosError;
        if (error.response?.status === 401) {
          console.log("401 error");
          const response = api.post("/auth/refresh", {
            refreshToken,
          });
          response
            .then((res) => {
              setToken(res.data.token);
              prevRequest.headers.Authorization = `Bearer ${res.data.token}`;
              return api.request(prevRequest);
            })
            .catch(() => {
              clearRefreshToken();
              clearToken();
              navigation("/login");
              toast({
                title: t("sessionExpired"),
                description: t("sessionExpiredDescription"),
              });
            });
        }
        return Promise.reject(error);
      }
    );

    return () => {
      api.interceptors.request.eject(requestInterceptor);
      api.interceptors.response.eject(responseInterceptor);
    };
  }, [
    token,
    refreshToken,
    setRefreshToken,
    clearRefreshToken,
    clearToken,
    setToken,
    navigation,
  ]);

  return { api };
};

export default useAxiosPrivate;
