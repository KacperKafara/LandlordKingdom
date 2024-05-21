import { FC } from "react";
import UserData from "./UserData";
import ChangeUserPassword from "./ChangeUserPassword";
import UpdateEmailAddress from "./UpdateEmailAddress";
import { useMeQuery } from "@/data/meQueries";
import DataField from "./DataField";
import { useTranslation } from "react-i18next";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  Card,
  CardContent,
  CardHeader,
  CardDescription,
  CardTitle,
} from "@/components/ui/card";
import RefreshQueryButton from "@/components/RefreshQueryButton";
const MePage: FC = () => {
  const { t } = useTranslation();
  const { data } = useMeQuery();
  return (
    <div className="w-full flex justify-center">
      <div className="flex flex-col gap-2 pb-6 w-10/12">
        <Card className=" mt-3 flex justify-center">
          <CardHeader>
            <CardTitle>{t("mePage.title")}</CardTitle>
          </CardHeader>
        </Card>
        <Tabs defaultValue="basic" className="self-center w-4/5">
          <TabsList>
            <TabsTrigger value="basic">{t("mePage.basicInformation")}</TabsTrigger>
            <TabsTrigger value="updateData">{t("mePage.updateData")}</TabsTrigger>
            <TabsTrigger value="changePassword">{t("mePage.changePassword")}</TabsTrigger>
            <TabsTrigger value="changeEmail">{t("mePage.changeEmail")}</TabsTrigger>
          </TabsList>
          <TabsContent value="basic">
            <Card className="relative mt-3">
              <div className="absolute top-0 right-0">
                <RefreshQueryButton queryKeys={["meData"]} />
              </div>
              <div>
                <CardHeader className=" items-center">
                  <CardTitle>{t("mePage.basicInformation")}</CardTitle>
                </CardHeader>
                <CardContent>
                  <div className="flex justify-center">
                    <div className="grid grid-cols-2 grid- w-2/3 gap-2">
                      <DataField
                        label={t("mePage.firstNameLabel")}
                        value={data?.data.firstName ?? "-"}
                      />
                      <DataField
                        label={t("mePage.lastNamelabel")}
                        value={data?.data.lastName ?? "-"}
                      />
                      <DataField
                        label={t("mePage.emailLabel")}
                        value={data?.data.email ?? "-"}
                      />
                      <div className="col-start-1">
                        <DataField
                          label={t("mePage.lastSuccessfullLoginDateLabel")}
                          value={data?.data.lastSuccessfulLogin ?? "-"}
                        />
                      </div>
                      <DataField
                        label={t("mePage.lastSuccessfillLoginIPLabel")}
                        value={data?.data.lastSuccessfulLoginIP ?? "-"}
                      />
                      <DataField
                        label={t("mePage.lastFailedfullLoginDateLabel")}
                        value={data?.data.lastFailedLogin ?? "-"}
                      />
                      <DataField
                        label={t("mePage.lastFailedfillLoginIPLabel")}
                        value={data?.data.lastFailedLoginIP ?? "-"}
                      />
                    </div>
                  </div>
                </CardContent>
              </div>
            </Card>
          </TabsContent>
          <TabsContent value="updateData">
            <Card className="relative mt-3 self-center">
              <div className="absolute top-0 right-0">
                <RefreshQueryButton queryKeys={["meData"]} />
              </div>
              <CardHeader className="items-center">
                <CardTitle >{t("mePage.updateData")}</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex justify-center">
                  <UserData />
                </div>
              </CardContent>
            </Card>
          </TabsContent>
          <TabsContent value="changePassword">
            <Card className="mt-3">
              <CardHeader className="items-center">
                <CardTitle>{t("mePage.changePassword")}</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="flex justify-center">
                  <ChangeUserPassword />
                </div>
              </CardContent>
            </Card>
          </TabsContent>
          <TabsContent value="changeEmail">

            <Card className="mt-3">
              <CardHeader className="items-center">
                <CardTitle >{t("mePage.changeEmail")}</CardTitle>
              </CardHeader>
              <CardContent className="pb-2">
                <div className="flex justify-center">
                  <UpdateEmailAddress />
                </div>
              </CardContent>
              <CardDescription className="flex justify-center px-6 pb-5 ">
                {t("mePage.changeEmailDescription")}
              </CardDescription>
            </Card>
          </TabsContent>
        </Tabs>










      </div>
    </div>
  );
};

export default MePage;
