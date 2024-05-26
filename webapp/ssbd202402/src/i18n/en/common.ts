import { Role } from "@/store/userStore";

const error = {
  baseTitle: "Error occured",
  baseDescription: "Something went wrong...",
  internalServerErrorDescription:
    "Oops! Something went wrong on our end. Please try again later.",
};

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
  googleLoginButton: "Sign in with Google",
  inactiveAccount: "Your account is inactive, check you e-mail to continue",
};

const userDetailsPage = {
  firstName: "First name",
  lastName: "Last name",
  login: "Login",
  email: "Email",
  language: "Language",
  lastSuccessfulLogin: "Last successful login",
  lastFailedLogin: "Last failed login",
  blocked: "Blocked",
  verified: "Verified",
  active: "Active",
  actions: "Actions",

  role: {
    title: "Roles",
    add: {
      tenant: "Add tenant role",
      owner: "Add owner role",
      administrator: "Add administrator role",
    },
    remove: {
      tenant: "Remove tenant role",
      owner: "Remove owner role",
      administrator: "Remove administrator role",
    },
  },
  changeEmail: "Change email address",
  updateEmailAddress: "Update email address",
  updateEmailAddressTitle:
    "Are you sure you want to update this user's email address?",
  updateEmailAddressDescription:
    "A link to change the email address will be sent to the given email address",
};

const updateEmailPage = {
  emailNotValid: "Email is not valid",
  email: "Email*",
  updateEmailButton: "Update email",
  updateEmailSuccess: "Email has been updated",
  updateEmailError: "Error while updating email",
  updateEmailTitle: "Enter your password",
  success: "Success",
  error: "Error",
  password: "Password*",
  confirmPassword: "Repeat password*",
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
  resetUserPasswordDescription: "Are you sure you want to reset user password ",
  resetUserPasswordToastTitleSuccess: "Operation successful",
  resetUserPasswordToastDescriptionSuccess:
    "Password reset link has been sent to the user email address",
  resetUserPasswordToastTitleFail: "Operation failed",
  resetUserPasswordToastDescriptionNotFound: "Not found given user.",
  resetUserPasswordToastDescriptionForbidden:
    "User is not verified or blocked.",
  resetUserPasswordToastDescriptionFail: "Something went wrong...",
  resetUserEmailAction: "Update email",
  resetUserEmailSuccess:
    "An email for changing current address has been sent to the given email address",
  resetUserEmailError: "Error while initializing email change",
  resetUserEmailTitle: "Are you sure you want to update user email address?",
  resetUserEmailDescription:
    "A link to change the email address will be sent to the user's email address",
  updateEmailAddress: "Update email address",
};

const block = {
  blockUserAction: "Block",
  unblockUserAction: "Unblock",
  toast: {
    title: {
      success: "Operation success",
      fail: "Operation failed",
    },

    description: {
      blockSuccess: "User sucessfully blocked.",
      unblockSuccess: "User sucessfully unblocked.",
      notFound: "Users not found.",
      alreadyBlocked: "User already blocked.",
      alreadyUnblocked: "User already unblocked.",
      fail: "Something went wrong...",
    },
  },
};

const userFilter = {
  yes: "Yes",
  no: "Nie",
  both: "Both",
  verified: "Verified",
  blocked: "Not verified",
  login: "Login",
  email: "Email",
  submit: "Filter",
  role: "Role",
  all: "All",
  tenant: "Tenant",
  owner: "Owner",
  administrator: "Administrator",
  lastName: "Last Name",
  clear: "Clear",
};

const pageChanger = {
  numberOfElements: "Number of elements",
  page: "Page",
  of: "of",
};

const common = {
  yes: "yes",
  no: "no",
  update: "Update",
  confirmDialogTitle: "Are you sure?",
};

const navLinks = {
  account: "My account",
  signOut: "Sign out",
  users: "Users",
  roles: "Change access level",
};

const userDataPage = {
  firstNameNotEmpty: "First name cannot be empty",
  lastNameNotEmpty: "Last name cannot be empty",
  firstName: "First name*",
  lastName: "Last name*",
  language: "Language*",
  error: "Error",
  success: "Data updated",
  updateUserData: "Update",
  emailNotValid: "Email is not valid",
  email: "Email*",
  emailNotEmpty: "Email cannot be empty",
  emailTooLong: "Email is too long",
  confirmDialogDescription: "Are you sure you want to chane personal data?",
};

const updateDataForm = {
  error: "Error during updating data",
  success: "Data updated",
  precondinationFailed: "You are not working on the latest data",
  triggerButton: "Update data",
  title: "Update user data",
  firstName: "First name",
  lastName: "Last name",
  language: "Language",
  updateButton: "Update",
  updateUserData: "Update user data",
};

const mePage = {
  accountInfo: "User data",
  updateEmailAddress: "Update email address",
  updateEmailAddressTitle:
    "Are you sure you want to update your email address?",
  updateEmailAddressDescription:
    "A link to change the email address will be sent to the user's email address",
  title: "My account",
  basicInformation: "Basic informations",
  firstNameLabel: "First name",
  lastNamelabel: "Last name",
  emailLabel: "Email",
  lastSuccessfullLoginDateLabel: "Last successful Login Date",
  lastSuccessfillLoginIPLabel: "Last successful Login IP",
  lastFailedfullLoginDateLabel: "Last failed Login Date",
  lastFailedfillLoginIPLabel: "Last failed Login IP",
  updateData: "Update your data",
  changeEmail: "Change your email address",
  changePassword: "Change your password",
  changeEmailDescription: "Click here to send email with link to change email.",
  emailInput: "Email*",
};

const registerSuccessPage = {
  title: "Thanks for creating an account",
  description:
    "We send you an email with verification link. Use this link to verify your account. Until you perform this action you won't be able to login.",
  loginButton: "Go back to login",
};

const validation = {
  characters: "character(s)",
  minLength: "Field must contain at least",
  maxLength: "Field must contain at most",
};

const roles = {
  administrator: "Administrator",
  tenant: "Tenant",
  owner: "Owner",
} satisfies {
  [key in Role]: string;
};

const homePage = {
  manageProperties: "Manage your properties with ease",
  signIn: "Sign in",
  signUp: "Create account",
  or: "or",
};

const sessionExpiredDialog = {
  title: "Session will expire soon!",
  description:
    "Your session will expire in less than 5 minutes. Do you want to extend it?",
    signOut: "Logout",
};

export default {
  sessionExpiredDialog,
  error,
  homePage,
  common,
  roles,
  registerSuccessPage,
  navLinks,
  loginPage,
  registerPage,
  resetPasswordForm,
  resetPasswordPage,
  changePasswordForm,
  userListPage,
  userFilter,
  block,
  userDetailsPage,
  updateDataForm,
  mePage,
  userDataPage,
  updateEmailPage,
  validation,
  pageChanger,
  sessionExpired: "Session expired",
  sessionExpiredDescription: "Session expired, please login again",
  footer: "Landlord Kingdom - SSBD202402",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Confirm",
  cancel: "Cancel",
} as const;
