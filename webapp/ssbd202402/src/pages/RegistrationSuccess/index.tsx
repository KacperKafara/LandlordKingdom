import { FC } from "react";

const RegistrationSuccessPage: FC = () => {
  return (
    <div>
      <h1>Thanks for creating an account</h1>
      <p>
        We send you an email with verification link. Use this link to verify
        your account. Until you perform this action you won't be able to login.
      </p>
    </div>
  );
};

export default RegistrationSuccessPage;
