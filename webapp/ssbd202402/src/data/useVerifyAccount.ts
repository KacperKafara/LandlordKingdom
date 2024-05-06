import { useMutation } from "@tanstack/react-query";
import { api } from "./api";
import { useToast } from "@/components/ui/use-toast";
import { useParams } from "react-router-dom";

export const useVerifyAccount = () => {
  const { token } = useParams<{ token: string }>();
  const { toast } = useToast();
  const { mutate, isSuccess } = useMutation({
    mutationKey: ["verify"],
    mutationFn: async () => {
      await api.post("/auth/verify", { token });
    },
    onError: () => {
      toast({
        variant: "destructive",
        title: "Error",
        description: "There was an error during account verification",
      });
    },
    onSuccess: () => {
      toast({
        variant: "default",
        title: "success",
      });
    },
  });

  return { verifyAccount: mutate, isSuccess };
};
