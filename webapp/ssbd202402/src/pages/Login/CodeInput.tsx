import { FC } from "react";
import {
  InputOTP,
  InputOTPGroup,
  InputOTPSlot,
} from "@/components/ui/input-otp";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { zodResolver } from "@hookform/resolvers/zod";
import { SubmitHandler, useForm } from "react-hook-form";
import { z } from "zod";

import { Button } from "@/components/ui/button";
import { TFunction } from "i18next";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import { useVerifyCode } from "@/data/useAuthenticate.ts";
import LoadingButton from "@/components/LoadingButton";
import { useTheme } from "@/components/ThemeProvider";
import { roleMapping, useUserStore } from "@/store/userStore";

const CodeFormSchema = (t: TFunction) =>
  z.object({
    pin: z.string().min(6, {
      message: t("loginPage.codeLengthMessage"),
    }),
  });

interface CodeInputProps {
  login: string;
  roles: string[] | undefined;
  setCodeInputOpen: (value: boolean) => void;
  resetForm: () => void;
}

type CodeSchema = z.infer<ReturnType<typeof CodeFormSchema>>;

const CodeInput: FC<CodeInputProps> = ({
  login,
  roles,
  setCodeInputOpen,
  resetForm,
}) => {
  const { t } = useTranslation();
  const form = useForm<CodeSchema>({
    resolver: zodResolver(CodeFormSchema(t)),
    defaultValues: {
      pin: "",
    },
  });
  const navigate = useNavigate();
  const { verifyCode, isPending } = useVerifyCode();
  const { setTheme } = useTheme();
  const { activeRole, setToken, setRefreshToken } = useUserStore();

  const onSubmit: SubmitHandler<CodeSchema> = async (data: CodeSchema) => {
    const result = await verifyCode({ login, token: data.pin });
    setToken(result.token);
    setRefreshToken(result.refreshToken);
    setTheme(result.theme);
    if (roles === undefined) {
      return navigate("/login");
    } else {
      navigate(`/${roleMapping[activeRole!]}`);
    }
  };

  return (
    <>
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit)}
          className="border-1 flex w-fit flex-col rounded-md bg-card p-7 pb-3 shadow-2xl shadow-shadowColor"
        >
          <FormField
            control={form.control}
            name="pin"
            render={({ field }) => (
              <FormItem className="text-center">
                <FormLabel className="text-2xl">One-Time Password</FormLabel>
                <FormControl>
                  <InputOTP maxLength={8} {...field}>
                    <InputOTPGroup className="">
                      <InputOTPSlot index={0} />
                      <InputOTPSlot index={1} />
                      <InputOTPSlot index={2} />
                      <InputOTPSlot index={3} />
                      <InputOTPSlot index={4} />
                      <InputOTPSlot index={5} />
                      <InputOTPSlot index={6} />
                      <InputOTPSlot index={7} />
                    </InputOTPGroup>
                  </InputOTP>
                </FormControl>
                <FormDescription>
                  {t("loginPage.codeDescription")}
                </FormDescription>
                <FormMessage />
              </FormItem>
            )}
          />
          <LoadingButton
            className="mt-4"
            type="submit"
            isLoading={isPending}
            text={t("loginPage.submit")}
          />
          <Button
            variant="link"
            className="w-fit self-center"
            onClick={() => {
              resetForm();
              setCodeInputOpen(false);
            }}
          >
            {t("loginPage.backToLoginForm")}
          </Button>
        </form>
      </Form>
    </>
  );
};
export default CodeInput;
