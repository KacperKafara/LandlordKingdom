import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { t } from "i18next";
import { FC } from "react";
import RoleRequestsList from "./RoleRequestsList";
import LocalAcceptList from "./LocalAcceptList";
import { Card } from "@/components/ui/card";

const NotApprovedActionsPage: FC = () => {
  return (
    <div className="flex justify-center">
      <div className="m-5 flex w-10/12 justify-center">
        <Tabs className="w-full" defaultValue="locals">
          <TabsList>
            <TabsTrigger value="locals">
              {t("notApprovedActionsPage.locals")}
            </TabsTrigger>
            <TabsTrigger value="roles">
              {t("notApprovedActionsPage.roleRequests")}
            </TabsTrigger>
          </TabsList>

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
    </div>
  );
};

export default NotApprovedActionsPage;
