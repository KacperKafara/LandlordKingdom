import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useGetRoleRequest } from "@/data/role-request/useGetRoleRequest";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { FC } from "react";

const RoleRequestPage: FC = () => {
  const breadcrumbs = useBreadcrumbs([
    {
      title: "Tenant",
      path: "/tenant",
    },
    {
      title: "Role Request",
      path: "/tenant/role-request",
    },
  ]);
  const { roleRequest, isError } = useGetRoleRequest();
  return (
    <div className="flex justify-center">
      <div className="w-10/12 pt-10">
        {breadcrumbs}
        <Card>
          <CardHeader>
            <CardTitle>Role Request</CardTitle>
          </CardHeader>
          <CardContent>
            {isError && (
              <div>
                You already placed a request for role at:{" "}
                {roleRequest?.createdAt.toString() ?? ""}
              </div>
            )}
            Press this button to place a request for a owner role.
            <Button disabled={!isError}>Request Owner role</Button>
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default RoleRequestPage;
