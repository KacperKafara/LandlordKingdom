import { useMutation } from "@tanstack/react-query";
import useAxiosPrivate from "../useAxiosPrivate.ts";
import { useTranslation } from "react-i18next";
import { useToast } from "@/components/ui/use-toast.ts";
import {AxiosError, AxiosInstance} from "axios";
import { ErrorCode } from "@/@types/errorCode.ts";

type UpdateLocalData = {
    id: string;
    name: string;
    description: string;
    state: string;
}

interface UpdateOwnLocalRequest {
    request: UpdateLocalData;
    etag: string;
}

const putLocalData = async (data: UpdateOwnLocalRequest, api: AxiosInstance) => {
    await api.put(`/me/locals/${data.request.id}`, data.request, {
        headers: {
            "If-Match": data.etag,
        },
    });
};

export const useUpdateLocalData = () => {
    const { toast } = useToast();
    const { t } = useTranslation();
    const { api } = useAxiosPrivate();

    return useMutation({
        mutationFn: (data: UpdateOwnLocalRequest) => putLocalData(data, api),
        onSettled: async (_, error) => {
            if (error) {
                const axiosError = error as AxiosError;
                toast({
                    variant: "destructive",
                    title: t("updateLocalPage.errorTitle"),
                    description: t(
                        `errors.${(axiosError.response?.data as ErrorCode).exceptionCode}`
                    ),
                });
            } else {
                toast({
                    variant: "success",
                    title: t("updateLocalPage.successTitle"),
                    description: t("updateLocalPage.successDescription"),
                });
            }
        },
    });
};
