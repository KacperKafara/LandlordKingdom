import { TFunction } from "i18next";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { z } from "zod";

const getResetPasswordSchema = (t: TFunction) =>
  z.object({
    email: z.string().email(t("resetPasswordForm.emailRequired")),
  });

type ResetPasswordSchema = z.infer<ReturnType<typeof getResetPasswordSchema>>;

const ResetPasswordForm: FC = () => {
  const { t } = useTranslation();

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <form className="border-1 bg-white rounded-md border-black p-7 w-1/4 flex flex-col shadow-2xl">
        <h1 className="self-center text-3xl font-bold">
          {t("logoPlaceholder")}
        </h1>
        <h2 className="self-center text-2xl pb-7 pt-3">
          {t("resetPasswordForm.resetPasswordHeader")}
        </h2>
      </form>
    </div>
  );
};

export default ResetPasswordForm;
