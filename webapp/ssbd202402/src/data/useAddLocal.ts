import { useMutation } from "@tanstack/react-query";
import { AxiosError } from "axios";
import { useToast } from "@/components/ui/use-toast";
import { useTranslation } from "react-i18next";
import { ErrorCode } from "@/@types/errorCode";
import useAxiosPrivate from "@/data/useAxiosPrivate.ts";

type AddLocalRequest = {
    name: string;
    description: string;
    size: number;
    address: Address;
    marginFee: number;
    rentalFee: number;
    ownerId: string;
};

type Address = {
    number: string;
    street: string;
    city: string;
    zip: string;
    country: string;
};

export const useAddLocal = () => {
    const { toast } = useToast();
    const { t } = useTranslation();
    const { api } = useAxiosPrivate();

    const { mutateAsync, isSuccess, isPending } = useMutation({
        mutationFn: async (data: AddLocalRequest) => {
            await api.post("/locals", data);
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

    return { addLocal: mutateAsync, isSuccess, isPending };
};
