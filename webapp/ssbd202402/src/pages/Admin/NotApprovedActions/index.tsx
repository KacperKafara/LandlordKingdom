import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { FC, useState } from "react";
import RoleRequestsList from "./RoleRequestsList";
import LocalAcceptList from "./LocalAcceptList";
import { Card } from "@/components/ui/card";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { useTranslation } from "react-i18next";

const NotApprovedActionsPage: FC = () => {
  const { t } = useTranslation();
  const [currentTab, setCurrentTab] = useState("locals");
  const breadcrumbs = useBreadcrumbs([
    {
      title: t("roles.administrator"),
      path: "/admin",
    },
    {
      title: t("notApprovedActionsPage.title"),
      path: "/admin/not-approved",
    },
  ]);

  return (
    <div className="flex flex-col justify-center">
      {breadcrumbs}
      <Tabs
        className="mt-5 w-full"
        value={currentTab}
        onValueChange={setCurrentTab}
      >
        <div className="flex justify-between">
          <TabsList>
            <TabsTrigger value="locals">
              {t("notApprovedActionsPage.locals")}
            </TabsTrigger>
            <TabsTrigger value="roles">
              {t("notApprovedActionsPage.roleRequests")}
            </TabsTrigger>
          </TabsList>
          <RefreshQueryButton
            queryKeys={
              currentTab === "locals" ? ["unapprovedLocals"] : ["roleRequests"]
            }
          />
        </div>

        <Card className="mt-2 w-full">
          <TabsContent value="locals">
            <LocalAcceptList />
          </TabsContent>
          <TabsContent value="roles">
            <RoleRequestsList />
          </TabsContent>
        </Card>
      </Tabs>
    </div>
  );
};

export default NotApprovedActionsPage;
