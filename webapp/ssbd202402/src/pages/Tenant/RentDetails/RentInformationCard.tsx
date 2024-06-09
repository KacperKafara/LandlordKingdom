import DataField from "@/components/DataField";
import { Card, CardContent } from "@/components/ui/card";
import { TenantOwnRents } from "@/types/tenant/rentForTenant";
import { getAddressString } from "@/utils/address";
import React from "react";

type RentInformationCardProps = {
  rent?: TenantOwnRents;
};

const RentInformationCardComponent = ({ rent }: RentInformationCardProps) => {
  return (
    <Card className="mt-5">
      <CardContent>
        <div className="grid grid-cols-2 gap-2">
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            Rent Details
          </h2>
          <DataField label="Start date" value={rent?.startDate ?? ""} />
          <DataField label="End date" value={rent?.endDate ?? ""} />
          <DataField label="Balance" value={rent?.balance ?? ""} />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            Local details
          </h2>
          <DataField label="Name" value={rent?.local.name ?? ""} />
          <DataField
            label="Address"
            value={getAddressString(rent?.local.address)}
          />
          <DataField
            label="Fee"
            value={
              (rent?.local?.marginFee ?? 0) + (rent?.local?.rentalFee ?? 0)
            }
          />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            Owner details
          </h2>
          <DataField
            label="Name"
            value={`${rent?.owner.firstName} ${rent?.owner.lastName}`}
          />
          <DataField label="Email" value={rent?.owner.email ?? ""} />
        </div>
      </CardContent>
    </Card>
  );
};

const RentInformationCard = React.memo(RentInformationCardComponent);

export default RentInformationCard;
