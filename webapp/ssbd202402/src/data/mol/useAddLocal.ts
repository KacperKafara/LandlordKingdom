import {useMutation} from "@tanstack/react-query";
import { AxiosError } from "axios";
import { ErrorCode } from "@/@types/errorCode.ts";
import useAxiosPrivate from "@/data/useAxiosPrivate.ts";
import {AddLocal} from "@/types/mol/Locals.ts";
import {toast} from "@/components/ui/use-toast.ts";
import {t} from "i18next";


export const useAddLocal = () => {
    const { api } = useAxiosPrivate();

    const { mutateAsync } = useMutation({
        mutationFn: async (data: AddLocal) => {
            const result = await api.post("/locals", data);
            return result.data;
        },
        onError: (error: AxiosError) => {
            toast({
                variant: "destructive",
                title: "Failed to add local",
                description: t(
                    `errors.${(error.response?.data as ErrorCode).exceptionCode}`
                ),
            });
        },
    });

    return { addLocal: mutateAsync};
};