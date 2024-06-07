import { useGetTenantArchivalRents } from "@/data/mol/useGetTenantArchivalRents";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import { FC } from "react";
import { useTranslation } from "react-i18next";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import DataField from "@/components/DataField";
import RefreshQueryButton from "@/components/RefreshQueryButton";


const ArchivalRentsPage : FC = () => {
    const { t } = useTranslation();
    const breadcrumbs = useBreadcrumbs([
        { title: "Tenant", path: "/tenant" },
        { title: t("navLinks.archivalRents"), path: "/tenant/archival-rents" },
    ]);
    const { data, isLoading } = useGetTenantArchivalRents();

    if (isLoading) {
        return <div>Loading...</div>;
    }
    if (!data) {
        return <div>No data</div>;
    }

    return (
        <div className='relative pt-2'>
        {breadcrumbs}
        <div className=' flex justify-center w-full'>
        
            <ul className='py-4 w-4/5 flex flex-wrap gap-2'>
                {data?.map((rent) => (
                    <li key={rent.id} className='flex-1 min-w-80'>
                        <Card className="">
                            <CardHeader>
                                <CardTitle className='text-2xl'>{rent.local.name}</CardTitle>
                                <CardDescription>
                                    {rent.local.address.country + ", " +
                                    rent.local.address.street + " " +
                                    rent.local.address.number + ", " +
                                    rent.local.address.zipCode.substring(0, 2) + "-" + rent.local.address.zipCode.substring(2) + " " +
                                    rent.local.address.city}
                                </CardDescription>
                                <p>
                                </p>
                            </CardHeader>
                            <CardContent className='grid grid-cols-2'>
                                <DataField label={t("tenantRents.startDate")} value={rent.startDate} />
                                <DataField label={t("tenantRents.endDate")} value={rent.endDate} />
                                <DataField label={t("tenantRents.fixedFee")} value={(rent.local.rentalFee + rent.local.marginFee).toString()} />
                                <DataField label={t("tenantRents.balance")} value={rent.balance.toString()} />
                                <DataField label={t("tenantRents.localSize")} value={rent.local.size.toString()} />
                                <p className='col-span-2 my-3 font-bold text-xl'>{t("tenantRents.owner")}</p>
                                <DataField label={t("tenantRents.name")} value={`${rent.owner.firstName} ${rent.owner.lastName}`} />
                                <DataField label={t("tenantRents.email")} value={rent.owner.email} />
                                <DataField label={t("tenantRents.login")} value={rent.owner.login} />
                            </CardContent>
                            <CardFooter className='w-full justify-center gap-3'>
                                <Button className='flex-auto'>Action 1</Button>
                                <Button className='flex-auto'>Action 2</Button>
                                <Button className='flex-auto'>Action 3</Button>
                            </CardFooter>
                        </Card>
                    </li>
                ))}
            </ul>
        </div>
        <div className='absolute -right-10 top-0 ' >
            <RefreshQueryButton queryKeys={["tenantArchivalRents"]} />
        </div>
    </div>
    )
}

export default ArchivalRentsPage;