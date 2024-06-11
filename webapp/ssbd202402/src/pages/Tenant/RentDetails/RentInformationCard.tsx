import DataField from "@/components/DataField";
import { Card, CardContent } from "@/components/ui/card";
import { TenantOwnRents } from "@/types/tenant/rentForTenant";
import { getAddressString } from "@/utils/address";
import React from "react";
import { useTranslation } from "react-i18next";

type RentInformationCardProps = {
  rent?: TenantOwnRents;
};

const RentInformationCardComponent = ({ rent }: RentInformationCardProps) => {
  const { t } = useTranslation();
  return (
    <Card className="mt-5">
      <CardContent>
        <div className="grid grid-cols-2 gap-2">
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            {t("rentDetailsPage.rentDetails")}
          </h2>
          <DataField
            label={t("rentDetailsPage.labels.startDate")}
            value={rent?.startDate ?? ""}
          />
          <DataField
            label={t("rentDetailsPage.labels.endDate")}
            value={rent?.endDate ?? ""}
          />
          <DataField
            label={t("rentDetailsPage.labels.balance")}
            value={(rent?.balance.toFixed(2) ?? 0) + " " + t("currency")}
          />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            {t("rentDetailsPage.localDetails")}
          </h2>
          <DataField
            label={t("rentDetailsPage.labels.localName")}
            value={rent?.local.name ?? ""}
          />
          <DataField
            label={t("rentDetailsPage.labels.address")}
            value={getAddressString(rent?.local.address)}
          />
          <DataField
            label={t("rentDetailsPage.labels.fixedFee")}
            value={
              (
                (rent?.local?.marginFee ?? 0) + (rent?.local?.rentalFee ?? 0)
              ).toFixed(2) +
              " " +
              t("currency")
            }
          />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            {t("rentDetailsPage.ownerDetails")}
          </h2>
          <DataField
            label={t("rentDetailsPage.labels.name")}
            value={`${rent?.owner.firstName} ${rent?.owner.lastName}`}
          />
          <DataField
            label={t("rentDetailsPage.labels.email")}
            value={rent?.owner.email ?? ""}
          />
        </div>
      </CardContent>
    </Card>
  );
};

const RentInformationCard = React.memo(RentInformationCardComponent);

export default RentInformationCard;
