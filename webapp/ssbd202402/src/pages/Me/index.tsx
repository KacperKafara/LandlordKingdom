import { FC } from "react";
import UserData from "./UserData";
import ChangeUserPassword from "./ChangeUserPassword";
import UpdateEmailAddress from "./UpdateEmailAddress";
import { useMeQuery } from "@/data/meQueries";
import DataField from "./DataField";

const MePage: FC = () => {
  const { data } = useMeQuery();
  return (
    <div className="flex flex-col w-10/12 gap-2 pb-6">
      <h1 className="text-3xl">My Account</h1>
      <h2 className="text-xl">Basic informations</h2>
      <div className="grid grid-cols-2 w-2/3 gap-2">
        <DataField label="First name" value={data?.data.firstName ?? ""} />
        <DataField label="Last name" value={data?.data.lastName ?? ""} />
        <DataField label="Email" value={data?.data.email ?? ""} />
        <DataField
          label="Last successful login date"
          value={data?.data.lastSuccessfulLogin ?? ""}
        />
        <DataField
          label="Last successful login IP"
          value={data?.data.lastSuccessfulLoginIP ?? ""}
        />
        <DataField
          label="Last failed login date"
          value={data?.data.lastFailedLogin ?? ""}
        />
        <DataField
          label="Last failed login IP"
          value={data?.data.lastFailedLoginIP ?? ""}
        />
      </div>
      <div className="border-b border-b-gray-500 w-full py-2" />
      <h2 className="text-xl">Update your data</h2>
      <UserData />
      <div className="border-b border-b-gray-500 w-full py-2" />
      <h2 className="text-xl">Change your email</h2>
      <UpdateEmailAddress />
      <div className="border-b border-b-gray-500 w-full py-2" />
      <h2 className="text-xl">Change your password</h2>
      <ChangeUserPassword />
    </div>
  );
};

export default MePage;
