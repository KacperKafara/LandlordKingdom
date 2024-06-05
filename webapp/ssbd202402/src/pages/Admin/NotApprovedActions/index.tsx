import { Tabs, TabsList, TabsTrigger, TabsContent } from "@/components/ui/tabs";
import { t } from "i18next";
import { FC } from "react";
import RoleRequestsList from "./RoleRequestsList";

const NotApprovedActionsPage: FC = () => {
  return (
    <>
      <div className="m-5 flex justify-center">
        <Tabs>
          <div className="flex justify-center">
            <TabsList>
              <TabsTrigger value="locals">
                {t("notApprovedActionsPage.locals")}
              </TabsTrigger>
              <TabsTrigger value="roles">
                {t("notApprovedActionsPage.roleRequests")}
              </TabsTrigger>
            </TabsList>
          </div>
          <TabsContent value="locals">TODO</TabsContent>
          <TabsContent value="roles">
            <RoleRequestsList />
          </TabsContent>
        </Tabs>
      </div>
    </>
  );
};

export default NotApprovedActionsPage;
