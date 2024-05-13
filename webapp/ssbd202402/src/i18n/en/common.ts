const loginPage = {
  forgotPassword: "Forgot password?",
  loginButton: "Sign in",
  login: "Login",
  password: "Password",
  loginHeader: "Sign in",
  register: "Sign up",
  loginRequired: "Login is required",
  passwordRequired: "Password is required",
  loginError: "Login error",
  invalidCredentials: "Invalid credentials",
  loginNotAllowed: "Login is blocked, check email",
  tryAgain: "Try again",
  codeLengthMessage: "Two-factor authentication code must be 8 digits",
  codeDescription: "Enter two-factor authentication code",
  changeLanguage: "Language",
  submit: "Submit",
  backToLoginForm: "Back to login form",
  tokenError: {
    title: "Incorrect data",
    description: "Provided incorrect token",
  },
};

const userDetailsPage = {
  goBack: "<-- Wróć placeholder",
  firstName: "First name",
  lastName: "Last name",
  login: "Login",
  email: "Email",
  language: "Language",
  lastSuccessfulLogin: "Last successful login",
  lastFailedLogin: "Last failed login",
  blocked: "Blocked",
  verified: "Verified",
  actions: "Actions",
};

const updateEmailPage = {
  emailNotValid: "Email is not valid",
  email: "Email*",
  updateEmailButton: "Update email",
  updateEmailSuccess: "Email has been updated",
  updateEmailError: "Error while updating email",
  updateEmailTitle: "Enter new email address",
  success: "Success",
  error: "Error",
};

const registerPage = {
  firstNameRequired: "First name is required",
  lastNameRequired: "Last name is required",
  emailRequired: "Email is required",
  loginRequired: "Login is required",
  passwordRequired: "Password must be at least 8 characters long",
  passwordMatch: "Passwords must match",
  registerHeader: "Sign up",
  firstName: "First name*",
  lastName: "Last name*",
  email: "Email*",
  login: "Login*",
  password: "Password*",
  confirmPassword: "Confirm password*",
  registerButton: "Sign up",
  registerSuccess:
    "Link to confirm registration has been sent to the provided email address.",
  registerError: "Error while registering",
  tryAgain: "Try again",
  registerErrorIdenticalFields: "Login and email must be different",
};

const resetPasswordForm = {
  description:
    "Enter your email address and we will send you a link to reset your password",
  email: "Email*",
  emailRequired: "Email is required",
  resetPassword: "Reset password",
  loginButton: "Back to login form",
  resetUserPasswordToastTitleSuccess: "Operation successful",
  resetUserPasswordToastDescriptionSuccess:
    "Link has been sent to the provided email address",
  resetUserPasswordToastTitleFail: "Operation failed",
  resetUserPasswordToastDescriptionNotFound:
    "User with provided email address not found",
  resetUserPasswordToastDescriptionFail: "Something went wrong...",
};

const resetPasswordPage = {
  header: "Reset password",
  password: "Password*",
  confirmPassword: "Confirm password*",
  confirmButton: "Reset password",
  homeButton: "Back to home",
  changePasswordToastTitleSuccess: "Operation successful",
  changePasswordToastDescriptionSuccess: "Password has been changed",
  changePasswordToastTitleFail: "Operation failed",
  changePasswordToastDescriptionFail: "Something went wrong...",
};

const changePasswordForm = {
  oldPassword: "Old password*",
  newPassword: "New password*",
  confirmPassword: "Confirm new password*",
  submit: "Change",
  success: "Success",
  errorTitle: "Error",
  errorDescriptionNotFound: "User not found",
  errorDescriptionBadRequest: "Incorrect password",
  alertDialogTitle: "Change password",
  alertDialogDescription: "Are you sure you want to change your password?",
  alertDialogCancel: "No",
  alertDialogAction: "Yes",
};

const userListPage = {
  firstName: "First name",
  lastName: "Last name",
  login: "Login",
  email: "Email",
  actions: "Actions",
  viewDetails: "Details",
  resetUserPasswordAction: "Reset password",
  resetUserPasswordTitle: "Reset user password",
  resetUserPasswordDescription: "Are you sure you want to reset user password?",
  resetUserPasswordToastTitleSuccess: "Operation successful",
  resetUserPasswordToastDescriptionSuccess:
    "Password reset link has been sent to the user email address",
  resetUserPasswordToastTitleFail: "Operation failed",
  resetUserPasswordToastDescriptionFail: "Something went wrong...",
  resetUserEmailAction: "Update email",
  resetUserEmailSuccess:
    "Email to change the address has been sent to the user",
  resetUserEmailError: "Error while initializing email change",
};

const common = {
  yes: "yes",
  no: "no",
};

const navLinks = {
  account: "My account",
};

const userDataPage = {
  firstNameNotEmpty: "First name cannot be empty",
  lastNameNotEmpty: "Last name cannot be empty",
  firstName: "First name*",
  lastName: "Last name*",
  language: "Language*",
  error: "Error",
  success: "Data updated",
};

const mePage = {
  accountInfo: "User data",
  updateEmailAddress: "Update email address",
};

export default {
  common,
  navLinks,
  loginPage,
  registerPage,
  resetPasswordForm,
  resetPasswordPage,
  changePasswordForm,
  userListPage,
  userDetailsPage,
  mePage,
  userDataPage,
  updateEmailPage,
  footer: "Landlord Kingdom - SSBD202402",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Confirm",
  cancel: "Cancel",
};
