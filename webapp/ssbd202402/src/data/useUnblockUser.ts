import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

export const useUnblockUser = () => {
    const { mutateAsync } = useMutation({
        mutationFn: async (userId: string) => {
            const response = await api.post(`/users/${userId}/unblock`);
            return response.status;
        },
    });

    return { unblockUser: mutateAsync };
};
