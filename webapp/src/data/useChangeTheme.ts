import { useMutation } from "@tanstack/react-query";
import { useUserStore } from '@/store/userStore';
import { api } from "./api";

export const useChangeTheme = () => {
    const { token } = useUserStore(state => ({ token: state.token }));

    const ThemeMutation = () => {
        return useMutation({
            mutationFn: async (theme: string) => {
                if (!token) {
                    return;
                }

                await api.post('/me/theme', { theme }, {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
            },
        });
    };

    return { ThemeMutation };
};