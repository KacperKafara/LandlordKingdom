import { FC } from "react";
import UserData from "./UserData";
import ChangeUserPassword from "./ChangeUserPassword";
import UpdateEmailAddress from "./UpdateEmailAddress";
import { useMeQuery } from "@/data/meQueries";
import DataField from "./DataField";
import { useTranslation } from "react-i18next";

const MePage: FC = () => {
  const { t } = useTranslation();
  const { data } = useMeQuery();
  return (
    <div className="w-full flex justify-center">
      <div className="flex flex-col gap-2 pb-6 w-10/12">
        <h1 className="text-3xl">{t("mePage.title")}</h1>
        <h2 className="text-xl">{t("mePage.basicInformation")}</h2>
        <div className="grid grid-cols-2 w-2/3 gap-2">
          <DataField
            label={t("mePage.firstNameLabel")}
            value={data?.data.firstName ?? ""}
          />
          <DataField
            label={t("mePage.lastNamelabel")}
            value={data?.data.lastName ?? ""}
          />
          <DataField
            label={t("mePage.emailLabel")}
            value={data?.data.email ?? ""}
          />
          <DataField
            label={t("mePage.lastSuccessfullLoginDateLabel")}
            value={data?.data.lastSuccessfulLogin ?? ""}
          />
          <DataField
            label={t("mePage.lastSuccessfillLoginIPLabel")}
            value={data?.data.lastSuccessfulLoginIP ?? ""}
          />
          <DataField
            label={t("mePage.lastFailedfullLoginDateLabel")}
            value={data?.data.lastFailedLogin ?? ""}
          />
          <DataField
            label={t("mePage.lastFailedfillLoginIPLabel")}
            value={data?.data.lastFailedLoginIP ?? ""}
          />
        </div>
        <div className="border-b border-b-gray-500 w-full py-2" />
        <h2 className="text-xl">{t("mePage.updateData")}</h2>
        <UserData />
        <div className="border-b border-b-gray-500 w-full py-2" />
        <h2 className="text-xl">{t("mePage.changeEmail")}</h2>
        <UpdateEmailAddress />
        <div className="border-b border-b-gray-500 w-full py-2" />
        <h2 className="text-xl">{t("mePage.changePassword")}</h2>
        <ChangeUserPassword />
      </div>
    </div>
  );
};

export default MePage;
