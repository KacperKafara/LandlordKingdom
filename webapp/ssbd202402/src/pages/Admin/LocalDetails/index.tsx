import { useGetLocalDetailsForAdmin } from "@/data/local/useGetLocalDetailsForAdmin";
import { FC } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import DataField  from "@/components/DataField";
import { Button } from "@/components/ui/button";
import { useTranslation } from "react-i18next";

const LocalDetailsPage: FC = () => {
    const {id } = useParams<{id: string}>();
    const {data, isLoading} = useGetLocalDetailsForAdmin(id!);
    const navigate = useNavigate();
    const { t } = useTranslation();


    if (isLoading) {
        return <div>Loading...</div>
    }

    return (
        <div className="py-10 flex flex-col items-center">
           {data && (
            <>
                <div className="w-10/12 ">
                    <Card>
                        <CardHeader className="items-center">
                            <CardTitle>{data.name}</CardTitle>
                        </CardHeader>
                        <CardContent>
                        <div className="flex justify-center">
                            <div className="grid w-2/3 grid-cols-2 gap-2">
                                <p className="col-span-2 text-xl font-semibold">Local information</p>   
                                <DataField label={t("localDetails.size")} value={data.size.toString()} />
                                <DataField label={t("localDetails.rentalFee")} value={data.rentalFee.toString()} />
                                <DataField label={t("localDetails.marginFee")} value={data.marginFee.toString()} />

                                <p className="col-span-2 text-xl font-semibold">{t("localDetails.ownerInformation")} </p>
                                
                                <DataField label={t("localDetails.firstName")} value={data.owner.firstName} />
                                <DataField label={t("localDetails.lastName")} value={data.owner.lastName} />
                                <DataField label={t("localDetails.login")} value={data.owner.login} />
                                <DataField label={t("localDetails.email")} value={data.owner.email} />

                                <p className="col-span-2 text-xl font-semibold">{t("localDetails.addressInformation")} </p>

                                <DataField label={t("localDetails.country")} value={data.address.country} />
                                <DataField label={t("localDetails.city")} value={data.address.city} />
                                <DataField label={t("localDetails.street")} value={data.address.street} />
                                <DataField label={t("localDetails.number")} value={data.address.number} />
                                <DataField label={t("localDetails.zipCode")} value={`${data.address.zipCode.substring(0,2)}${data.address.zipCode.substring(2)}`} />

                                
                                <div className="flex flex-col col-span-2">
                                    <div className="text-sm font-semibold">{t("localDetails.description")}</div>
                                <div>{data.description}</div>
                                </div>

                                <Button variant="secondary" className="mt-3 text-lg font-normal col-span-2" onClick={() => {navigate(`/admin/users/${data.owner.userId}`)}}>
                                    {t("localDetails.showOwnerDetails")}
                                </Button>
                            </div>
                        </div>
                        </CardContent>
                    </Card>
                </div>
            
            </>
              
           )}
        </div>
    )
}

export default LocalDetailsPage;