import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Button } from "@/components/ui/button";
import { Card, CardContent } from "@/components/ui/card";
import { RentDetailsForOwner } from "@/types/owner/RentDetailsForOwner";
import { getAddressString } from "@/utils/address";
import { toLocaleFixed } from "@/utils/currencyFormat";
import React from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";

type RentInformationCardProps = {
  rent?: RentDetailsForOwner;
};

const RentInformationCardComponent = ({ rent }: RentInformationCardProps) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <Card className="relative">
      <RefreshQueryButton
        className="absolute right-0 top-0"
        queryKeys={["rentDetailsForOwner"]}
      />
      <CardContent>
        <div className="grid grid-cols-2 gap-2">
          <h2 className="col-span-2 pt-4 text-xl font-semibold ">
            {t("ownerRentDetails.rentInfo")}
          </h2>
          <DataField
            label={t("ownerRentDetails.startDate")}
            value={rent?.startDate ?? ""}
          />
          <DataField
            label={t("ownerRentDetails.endDate")}
            value={rent?.endDate ?? ""}
          />
          <DataField
            label={t("ownerRentDetails.balance")}
            value={toLocaleFixed(rent?.balance ?? 0.0) + " " + t("currency")}
          />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            {t("ownerRentDetails.localInfo")}
          </h2>
          <DataField
            label={t("ownerRentDetails.localName")}
            value={rent?.local.name ?? ""}
          />
          <DataField
            label={t("ownerRentDetails.address")}
            value={getAddressString(rent?.local.address)}
          />
          <DataField
            label="Margin Fee"
            value={
              toLocaleFixed(rent?.local?.marginFee ?? 0.0) + " " + t("currency")
            }
          />
          <DataField
            label="Rental Fee"
            value={
              toLocaleFixed(rent?.local?.rentFee ?? 0.0) + " " + t("currency")
            }
          />
          <h2 className="col-span-2 pt-4 text-xl font-semibold">
            {t("ownerRentDetails.tenantInfo")}
          </h2>
          <DataField
            label={t("ownerRentDetails.name")}
            value={`${rent?.tenant.firstName} ${rent?.tenant.lastName}`}
          />
          <DataField
            label={t("ownerRentDetails.email")}
            value={rent?.tenant.email ?? ""}
          />
          <div className="col-span-2 flex gap-3">
            <Button
              variant="secondary"
              className="mt-3 w-full text-lg font-normal"
              onClick={() => {
                navigate(`/owner/locals/local/${rent?.local.id}`);
              }}
            >
              {t("ownerRentDetails.showLocalDetails")}
            </Button>
          </div>
        </div>
      </CardContent>
    </Card>
  );
};

const RentInformationCard = React.memo(RentInformationCardComponent);

export default RentInformationCard;
