import { FC } from 'react';
import { useGetTenantOwnRents } from '@/data/mol/useGetTenantOwnRents';
import {
    Card,
    CardContent,
    CardDescription,
    CardFooter,
    CardHeader,
    CardTitle,
  } from "@/components/ui/card";
import DataField from '@/components/DataField';
import RefreshQueryButton from '@/components/RefreshQueryButton';
import { Button } from '@/components/ui/button';

const CurrentRentsPage: FC = () => {
    const { data, isLoading } = useGetTenantOwnRents();

    if (isLoading) {
        return <div>Loading...</div>;
    }

    return (
        <div className='relative flex justify-center w-full'>
            
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
                                <p className='col-span-2 my-3 font-bold text-xl'>Local</p>

                                <DataField label='Start Date' value={rent.startDate} />
                                <DataField label='End Date' value={rent.endDate} />
                                <DataField label='Fixed Fee' value={(rent.local.rentalFee + rent.local.marginFee).toString()} />
                                <DataField label='Balance' value={rent.balance.toString()} />

                                <p className='col-span-2 my-3 font-bold text-xl'>Owner</p>

                                <DataField label='Name' value={`${rent.owner.firstName} ${rent.owner.lastName}`} />
                                <DataField label='Email' value={rent.owner.email} />
                                <DataField label='Login' value={rent.owner.login} />                                
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
            <div  className='absolute -right-10 top-0 ' >
                <RefreshQueryButton queryKeys={["tenantOwnRents"]} />
            </div>
        </div>
    );

}

export default CurrentRentsPage;