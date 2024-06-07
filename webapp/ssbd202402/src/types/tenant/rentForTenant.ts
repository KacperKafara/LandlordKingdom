export type TenantOwnRents = {
    id: string;
    local: {
        name: string;
        description: string;
        size: number;
        address: {
            country: string;
            city: string;
            street: string;
            number: string;
            zipCode: string;
        }
        marginFee: number;
        rentalFee: number;
    }
    owner: {
        firstName: string;
        lastName: string;
        login: string;
        email: string;
    }
    startDate: string;
    endDate: string;
    balance: number;

}