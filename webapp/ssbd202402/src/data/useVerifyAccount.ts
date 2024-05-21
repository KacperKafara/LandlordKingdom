import { useMutation } from "@tanstack/react-query";
import { useToast } from "@/components/ui/use-toast";
import { useParams } from "react-router-dom";
import useAxiosPrivate from "./useAxiosPrivate";

export const useVerifyAccount = () => {
  const { token } = useParams<{ token: string }>();
  const { toast } = useToast();
  const { api } = useAxiosPrivate();

  const { mutate, isSuccess } = useMutation({
    mutationKey: ["verify"],
    mutationFn: async () => {
      await api.post("/me/verify", { token });
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
