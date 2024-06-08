import { OwnLocals } from "./Locals";
import { TenantForOwner } from "./Tenant";

export interface OwnerCurrentRent {
  id: string;
  startDate: string;
  endDate: string;
  balance: number;
  local: OwnLocals;
  tenant: TenantForOwner;
}
