import { useMutation } from "@tanstack/react-query";
import { api } from "./api";

export const useBlockUser = () => {
    const { mutateAsync } = useMutation({
        mutationFn: async (userId: string) => {
            const response = await api.post(`/users/${userId}/block`);
            return response.status;
        },
    });

    return { blockUser: mutateAsync };
};
