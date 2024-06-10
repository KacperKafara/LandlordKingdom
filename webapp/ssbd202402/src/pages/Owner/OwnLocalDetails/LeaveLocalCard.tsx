import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { LeaveLocalButton } from "./LeaveLocalButton";
import { LocalState } from "@/@types/localState";

type LeaveLocalProps = {
  id: string;
  state: LocalState;
};

const LeaveLocalCard: FC<LeaveLocalProps> = ({ id, state }) => {
  const { t } = useTranslation();

  return (
    <Card>
      <CardHeader>
        <CardTitle className="text-center">
          {t("ownLocalDetails.leaveLocal")}
        </CardTitle>
      </CardHeader>
      <CardContent className="flex justify-center">
        <div className="flex w-4/5 flex-col">
          <p className="text-lg font-semibold">
            {t("ownLocalDetails.leaveLocalDescription")}
          </p>
          <LeaveLocalButton
            className="mt-2"
            disabled={state !== "INACTIVE"}
            id={id}
          />
        </div>
      </CardContent>
    </Card>
  );
};

export default LeaveLocalCard;
