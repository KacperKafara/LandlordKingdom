import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast";

export const useReactivateUser = () => {
  const { toast } = useToast();
  const { mutateAsync } = useMutation({
    mutationFn: async (token: string) => {
      await api.post(`/auth/reactivate?token=${token}`);
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "Account reactivated",
        description: "Your account has been reactivated. You can now login.",
      });
    },
    onError: (error) => {
      toast({
        variant: "destructive",
        title: "Error reactivating account",
        description: error.message,
      });
    },
  });

  return { reactivate: mutateAsync };
};
