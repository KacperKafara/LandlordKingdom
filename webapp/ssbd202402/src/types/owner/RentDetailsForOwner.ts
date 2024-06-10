import { Address } from "../Address";

export type RentDetailsForOwner = {
    id: string;
    local: {
        id: string;
        name: string;
        description: string;
        size: number;
        marginFee: number;
        rentFee: number;
        address: Address;
    }
    tenant: {
        login: string;
        firstName: string;
        lastName: string;
        email: string;
    }
    startDate: string;
    endDate: string;
    balance: number;
}
