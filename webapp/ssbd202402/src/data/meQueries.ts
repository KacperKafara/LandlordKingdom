import {api} from "@/data/api.ts";
import {useMutation, useQuery, useQueryClient} from "@tanstack/react-query";
import {UserResponse} from "@/types/user/UserResponseType.ts";
import {UserUpdateRequestType} from "@/types/user/UserUpdateRequestType.ts";
import {useToast} from "@/components/ui/use-toast.ts";
import {useTranslation} from "react-i18next";


const getMeData = async () => {
    return (await api.get<UserResponse>("/me")).data;
}

const putMeData = async (data: UserUpdateRequestType) => {
    await api.put("/me", data);
}

export const useMeQuery = () => {
    return useQuery(
        {
            queryKey: ["meData"],
            queryFn: getMeData
        }
    )
}

export const usMeMutation = () => {
    const queryClient = useQueryClient();
    const {toast} = useToast();
    const {t} = useTranslation();
    return useMutation(
        {
            mutationFn: putMeData,
            onSettled: async (_, error) => {
                if (error) {
                    toast({
                        variant: "destructive",
                        title: t("userDataPage.error"),
                        description: error.message,
                    });
                } else {
                    await queryClient.invalidateQueries({queryKey: ["meData"]});
                    toast({
                        title: t("userDataPage.success"),
                    });
                }
            }
        }
    )
}