import { ExceptionCode } from "@/@types/errorCode";
import { LocalState } from "@/@types/localState";
import { Role } from "@/store/userStore";

const error = {
  baseTitle: "Wystąpił błąd",
  baseDescription: "Coś poszło nie tak...",
  userBlocked: "Twoje konto jest zablokowane",
  internalServerErrorDescription:
    "Ups! Coś poszło nie tak po naszej stronie. Proszę spróbuj ponownie później.",
};

const loginPage = {
  forgotPassword: "Zapomniałeś hasła?",
  loginButton: "Zaloguj się",
  login: "Login",
  password: "Hasło",
  loginHeader: "Zaloguj się",
  register: "Zarejestruj się",
  loginRequired: "Login jest wymagany",
  passwordRequired: "Hasło jest wymagane",
  loginError: "Błąd logowania",
  invalidCredentials: "Nieprawidłowe dane logowania",
  loginNotAllowed: "Logowanie jest zablokowane, sprawdź email",
  tryAgain: "Spróbuj ponownie",
  codeLengthMessage: "Kod uwierzytelniania dwufazowego musi mieć 8 cyfr",
  codeDescription: "Wprowaź kod uwierzytelniania dwufazowego",
  changeLanguage: "Język",
  submit: "Wyślij",
  backToLoginForm: "Wróć do formularza logowania",
  tokenError: {
    title: "Błąd danych",
    description: "Wprowadzono niepoprawny żeton",
  },
  googleLoginButton: "Zaloguj się z Google",
  inactiveAccount:
    "Twoje konto jest nieaktywne, by kontynuować sprawdź skrzynkę pocztową",
};

const userDetailsPage = {
  goBack: "<-- Wróć placeholder",
  firstName: "Imię",
  lastName: "Nazwisko",
  login: "Login",
  email: "Email",
  language: "Język",
  lastSuccessfulLogin: "Ostatnie udane logowanie",
  lastFailedLogin: "Ostatnie nieudane logowanie",
  timezone: "Strefa czasowa",
  blocked: "Zablokowany",
  verified: "Zweryfikowany",
  active: "Aktywny",
  actions: "Akcje",

  role: {
    title: "Role",
    add: {
      tenant: "Przyznaj role najemcy",
      owner: "Przyznaj role właściciela",
      administrator: "Przyznaj role administratora",
    },
    remove: {
      tenant: "Odbierz role najemcy",
      owner: "Odbierz role właścicela",
      administrator: "Odbierz role administratora",
    },
    added: {
      tenant: "Przyznano role najemcy",
      owner: "Przyznano role własciciela",
      administrator: "Przyznano role administratora",
    },
    removed: {
      tenant: "Odebrano role najemcy",
      owner: "Odebrano role własciciela",
      administrator: "Odebrano role administratora",
    },
  },
  changeEmail: "Zmień adres email",
  updateEmailAddress: "Zaktualizuj adres email",
  updateEmailAddressTitle:
    "Czy na pewno chcesz zaktualizować adres email tego użytkownika?",
  updateEmailAddressDescription:
    "Na podany adres email zostanie wysłany link do zmiany adresu email",
};

const updateEmailPage = {
  emailNotValid: "Email nie jest poprawny",
  email: "Email*",
  updateEmailButton: "Zaktualizuj email",
  updateEmailSuccess: "Email został zaktualizowany",
  updateEmailError: "Wystąpił błąd podczas aktualizacji emaila",
  updateEmailTitle: "Wprowadź swoje hasło",
  success: "Sukces",
  error: "Błąd",
  password: "Hasło*",
  confirmPassword: "Powtórz hasło*",
};

const registerPage = {
  firstNameRequired: "Imię jest wymagane",
  lastNameRequired: "Nazwisko jest wymagane",
  emailRequired: "Email jest wymagany",
  loginRequired: "Login jest wymagany",
  passwordRequired: "Hasło musi składać się z co najmniej 8 znaków",
  passwordMatch: "Hasła muszą być takie same",
  registerHeader: "Zarejestruj się",
  firstName: "Imię*",
  lastName: "Nazwisko*",
  email: "Email*",
  login: "Login*",
  password: "Hasło*",
  confirmPassword: "Powtórz hasło*",
  registerButton: "Zarejestruj się",
  registerSuccess:
    "Link do potwierdzenia rejestracji został wysłany na podany adres email.",
  registerError: "Wystąpił błąd podczas rejestracji.",
  tryAgain: "Spróbuj ponownie",
  registerErrorIdenticalFields:
    "Użytkownik o podanym loginie lub emailu już istnieje",
};

const resetPasswordForm = {
  description:
    "Podaj adres email, na który zostanie wysłany link do zresetowania hasła",
  email: "Email*",
  emailRequired: "Email jest wymagany",
  resetPassword: "Zresetuj hasło",
  loginButton: "Wróć do formularza logowania",
  resetUserPasswordToastTitleSuccess: "Operacja powiodła się",
  resetUserPasswordToastDescriptionSuccess:
    "Link został wysłany na podany adres email",
  resetUserPasswordToastTitleFail: "Operacja nie powiodła się",
  resetUserPasswordToastDescriptionNotFound:
    "Użytkownik o podanym adresu email nie istnieje",
  resetUserPasswordToastDescriptionFail: "Coś poszło nie tak...",
};

const resetPasswordPage = {
  header: "Zresetuj hasło",
  password: "Hasło*",
  confirmPassword: "Powtórz hasło*",
  confirmButton: "Zresetuj hasło",
  homeButton: "Powrót",
  changePasswordToastTitleSuccess: "Operacja powiodła się",
  changePasswordToastDescriptionSuccess: "Twoje hasło zostało zmienione",
  changePasswordToastTitleFail: "Operacja nie powiodła się",
  changePasswordToastDescriptionFail: "Coś poszło nie tak...",
  changePasswordToastDescriptionTokenNotValid:
    "Podany żeton nie jest poprawny, wymagane ponowne wysłanie maila.",
  changePasswordToastDescriptionForbidden: "Jesteś zablokowany.",
};

const changePasswordForm = {
  oldPassword: "Obecne hasło*",
  newPassword: "Nowe hasło*",
  confirmPassword: "Potwierdź nowe hasło*",
  submit: "Zmień",
  success: "Sukces",
  successDescription: "Hasło zostało zmienione",
  errorTitle: "Wystąpił błąd",
  errorDescriptionNotFound: "Nie znaleziono użytkownika",
  errorDescriptionBadRequest: "Wprowadzono niepoprawne hasło",
  errorDescriptionConflict: "Hasło musi być unikalne względem poprzednich",
  errorDescriptionTokenNotValid:
    "Podany żeton nie jest poprawny, wymagane ponowne wysłanie maila.",
  alertDialogTitle: "Potwierdzenie zmiany hasła",
  alertDialogDescription: "Czy na pewno chcesz zmienić hasło?",
  alertDialogCancel: "Nie",
  alertDialogAction: "Tak",
};

const userListPage = {
  firstName: "Imię",
  lastName: "Nazwisko",
  login: "Login",
  email: "Email",
  actions: "Akcje",
  viewDetails: "Szczegóły",
  resetUserPasswordAction: "Zresetuj hasło",
  resetUserPasswordTitle: "Zresetuj hasło użytkownika",
  resetUserPasswordDescription:
    "Czy na pewno chcesz zresetować hasło użytkownika ",
  resetUserPasswordToastTitleSuccess: "Operacja powiodła się",
  resetUserPasswordToastDescriptionSuccess:
    "Do użytkownika została wysłana wiadomość email",
  resetUserPasswordToastTitleFail: "Operacja nie powiodła się",
  resetUserPasswordToastDescriptionFail: "Coś poszło nie tak...",
  resetUserPasswordToastDescriptionNotFound: "Podany użytkownik nie istanieje.",
  resetUserPasswordToastDescriptionForbidden:
    "Użytkownik jest niezweryfikowany albo zablokowany.",
  resetUserEmailAction: "Zaktualizuj adres email",
  resetUserEmailSuccess:
    "Wiadomość email do zmiany adresu została wysłana na podany adres email",
  resetUserEmailError: "Błąd podczas inicjalizacji zmiany adresu email  ",
  updateEmailAddressTitle:
    "Czy na pewno chcesz zaktualizować adres email użytkownika?",
  updateEmailAddressDescription:
    "Na adres email użytkownika zostanie wysłany link do zmiany adresu email",
  updateEmailAddress: "Zaktualizuj adres email",
};

const block = {
  blockUserAction: "Zablokuj",
  unblockUserAction: "Odblokuj",
  toast: {
    title: {
      success: "Operacja powiodła się",
      fail: "Operacja nie powiodła się",
    },

    description: {
      blockSuccess: "Użytkownik pomyślnie zablokowany",
      unblockSuccess: "Użytkownik pomyślnie odblokowany",
      notFound: "Podany użytkownik nie istnieje",
      alreadyBlocked: "Użytkownik jest już zablokowany.",
      alreadyUnblocked: "Użytkownik jest już odblokowany",
      fail: "Coś poszło nie tak...",
    },
  },
};

const userFilter = {
  yes: "Tak",
  no: "Nie",
  both: "Oba",
  verified: "Zweryfikowany",
  blocked: "Zablokowany",
  login: "Login",
  email: "Email",
  submit: "Filtruj",
  role: "Rola",
  all: "Wszystkie",
  tenant: "Najemca",
  owner: "Właściciel",
  administrator: "Administrator",
  clear: "Wyczyść",
  firstName: "Imię",
  lastName: "Nazwisko",
};

const pageChanger = {
  numberOfElements: "Liczba elementów",
  page: "Strona",
  of: "z",
};

const common = {
  yes: "tak",
  no: "nie",
  update: "Zaktualizuj",
  confirmDialogTitle: "Czy jesteś pewny?",
};

const navLinks = {
  account: "Moje konto",
  signOut: "Wyloguj",
  users: "Użytkownicy",
  notApprovedActions: "Niezatwierdzone operację",
  roles: "Zmień poziom dostępu",
  locals: "Nieruchomości",
  currentRents: "Aktualne wynajmy",
  archivalRents: "Archiwalne wynajmy",
  addLocal: "Dodaj lokal",
  applications: "Wnioski o wynajem",
};

const userDataPage = {
  firstNameNotEmpty: "Imię nie może być puste",
  lastNameNotEmpty: "Imię nie może być puste",
  firstName: "Imię*",
  lastName: "Nazwisko*",
  language: "Język*",
  error: "Błąd",
  success: "Dane zaktualizowane",
  updateUserData: "Aktualizuj",
  emailNotValid: "Email nie jest poprawny",
  email: "Email*",
  emailNotEmpty: "Email nie może być pusty",
  emailTooLong: "Email jest za długi",
  confirmDialogDescription: "Czy na pewno chcesz zmienić dane personalne?",
  timeZone: "Strefa czasowa",
};

const updateDataForm = {
  error: "Błąd podczas aktualizacji danych",
  success: "Dane zaktualizowane",
  precondinationFailed: "Nie pracujesz na najnowszych danych",
  triggerButton: "Aktualizuj dane",
  title: "Aktualizacja danych użytkownika",
  firstName: "Imię",
  lastName: "Nazwisko",
  language: "Język",
  updateButton: "Aktualizuj",
  updateUserData: "Aktualizuj dane użytkownika",
};

const mePage = {
  accountInfo: "Dane użytkownika",
  updateEmailAddress: "Zaktualizuj adres email",
  updateEmailAddressTitle: "Czy na pewno chcesz zaktualizować adres email?",
  updateEmailAddressDescription:
    "Na przypisany do konta adres email zostanie wysłany link do formularza zmiany adresu email",
  title: "Moje konto",
  basicInformation: "Podstawowe informacje",
  firstNameLabel: "Imię",
  lastNamelabel: "Nazwisko",
  emailLabel: "Email",
  lastSuccessfullLoginDateLabel: "Data ostatniego udanego logowania",
  lastSuccessfillLoginIPLabel: "Adres ip ostatniego udanego logowania",
  lastFailedfullLoginDateLabel: "Data ostatniego nieudanego logowania",
  lastFailedfillLoginIPLabel: "Adres ip ostatniego nieudanego logowania",
  updateData: "Zmień swoje dane",
  changeEmail: "Zmień swój adres email",
  changePassword: "Zmień swoje hasło",
  changeEmailDescription:
    "Naciśnij przycisk, aby otrzymać wiadomość email z linkiem do zmiany adresu email.",
  emailInput: "Email*",
  timezone: "Strefa czasowa",
};

const addLocalPage = {
  title: "Dodaj nowy lokal",
  name: "Nazwa",
  description: "Opis",
  size: "Rozmiar",
  number: "Numer mieszkania",
  street: "Nazwa ulicy",
  city: "Miasto",
  zip: "Kod pocztowy",
  country: "Kraj",
  marginFee: "Opłata stała",
  rentalFee: "Cena wynajmu",
  formSubmit: "Zgłoś formularz",
  wrong: {
    name: "Niepoprawna nazwa lokalu",
    description: "Niepoprawny opis lokalu",
    size: "Niepoprawny rozmiar",
    number: "Niepoprawny numer mieszkania",
    street: "Niepoprawna nazwa ulicy",
    city: "Niepoprawne miasto",
    zip: "Niepoprawny kod pocztowy",
    country: "Niepoprawny kraj",
    marginFee: "Niepoprawna opłata stała",
    rentalFee: "Niepoprawna cena wynajmu",
  },
  error: "Błąd podczas dodawania nieruchomości",
  successTitle: "Nieruchomość dodana",
  successDescription: "Nieruchomość została dodana",
};

const registerSuccessPage = {
  title: "Dziękujemy za stworzenie konta",
  description:
    "Wysłaliśmy ci email z linkiem slużącym do aktywacji konta. Użyj tego linku aby zweryfikować swój adres email. Dopóki nie zweryfikujesz adresu, nie możesz się zalogować.",
  loginButton: "Wróc do strony logowania",
};

const notApprovedActionsPage = {
  title: "Niezatwierdzone operacje",
  roleRequests: "Zgłoszenia o rolę",
  locals: "Lokale",
  emptyRoleRequests: "Brak zgłoszeń o rolę",
  emptyUnapprovedLocals: "Brak niezatwierdzonych lokali",
  actions: "Akcje",
  confirm: "Zatwierdź",
  reject: "Odrzuć",
  confirmDialog: {
    title: "Czy jesteś pewny?",
    confirmDescription: "Czy na pewno chcesz zatwierdzić ten lokal?",
    rejectDescription: "Czy na pewno chcesz odrzucić ten lokal?",
  },
  unapprovedLocals: {
    name: "Nazwa",
    address: "Adres",
    owner: "Właściciel",
    details: "Szczegóły",
  },
  approve: "Zaakceptuj",
  show: "Pokaż",
  roleRequestApproveSuccess: "Zgłoszenie o rolę zaakceptowane",
  roleRequestRejectSuccess: "Zgłoszenie o rolę odrzucone",
};

const validation = {
  characters: "znak(i/ów)",
  minLength: "Pole musi zawierać minimalnie",
  maxLength: "Pole musi zawierać maksymalnie",
};

const roles = {
  administrator: "Administrator",
  tenant: "Najemnca",
  owner: "Właściciel",
} satisfies {
  [key in Role]: string;
};

const homePage = {
  manageProperties: "Zarządzaj swoimi nieruchomościami z łatwością",
  signIn: "Zaloguj się",
  signUp: "Zarejestruj się",
  or: "lub",
};

const sessionExpiredDialog = {
  title: "Sesja wktórce wygaśnie",
  description:
    "Twoja sesja wygaśnie za 5 minut. Kliknij przycisk, aby ją przedłużyć",
  signOut: "Wyloguj",
};

const errors = {
  optimisticLock: "Nie pracujesz na najnowszych danych",
  registrationError: "Błąd podczas rejestracji",
  identicalLoginOrEmail: "Użytkownik o podanym loginie lub emailu już istnieje",
  invalidData: "Nieprawidłowe dane",
  invalidLoginData: "Nieprawidłowe dane logowania",
  invalidPassword: "Nieprawidłowe dane logowania",
  loginNotMatchToOTP: "Nieprawidłowy kod uwierzytelniania dwufazowego",
  passwordRepetition: "Hasło musi się różnić od poprzednich haseł",
  invalidRefreshToken: "Bład podczas odświeżania sesji",
  signInBlocked: "Logowanie jest zablokowane, sprawdź email",
  userAlreadyBlocked: "Użytkownik jest już zablokowany",
  userAlreadyUnblocked: "Użytkownik jest już odblokowany",
  userBlocked: "Twoje konto jest zablokowane",
  userInactive: "Twoje konto jest nieaktywne, sprawdź email",
  userNotVerified: "Twoje konto nie jest zweryfikowane, sprawdź email",
  verificationTokenExpired: "Twój kod wygasł",
  verificationTokenUsed: "Nieprawidłowy kod logowania",
  administratorOwnRoleRemoval: "Nie możesz usunąć swojej roli administratora",
  administratorOwnBlock: "Nie możesz zablokować swojego konta",
  notFound: "Nie znaleziono",
  userNotFound: "Użytkownik nie znaleziony",
  themeNotFound: "Nie znaleziono motywu",
  somethingWentWrong: "Coś poszło nie tak...",
  accessDenied: "Brak dostępu",
  jwtTokenInvalid: "Sesja wygasła",
  validationError: "Błąd walidacji",
  identicalEmail: "Użytkownik o podanym adresie email już istnieje",
  internalServerError: "Coś poszło nie tak po naszej stronie",
  accessLevelAssigned: "Poziom dostępu został już nadany",
  accessLevelTaken: "Poziom dostępu został już zabrany",
  undefined: "Wystpi nieoczekiwany błąd",
  localNotFound: "Nie znaleziono nieruchomości",
  localNotActive: "Nieruchomość musi być aktywna, aby wykonać tę operację",
  localNotInactive: "Nieruchomość musi być nieaktywna, aby wykonać tę operację",
  localNotUnapproved:
    "Nieruchomość musi być niezatwierdzona, aby wykonać tę operację",
  wrongEndDate:
    "Data zakończenia musi być późniejsza niż data rozpoczęcia, musi być w przyszłości i musi być Niedzielą",
  rentNotFound: "Nie znaleziono wynajmu",
  invalidLocalStateArchive:
    "Nie można zarchiwizować nieruchomości. Lokal nie może posiadać właściciela",
  addressAlreadyAssigned: "Adres jest już przypisany do innej nieruchomości",
  rentEnded: "Wynajem zakończony",
  variableFeeAlreadyExists: "Opłata zmienna już istnieje",
  paymentAlreadyExists: "Opłata stała już istnieje",
  dateParsingError: "Błąd parsowania daty",
  rollback: "Operacja została cofnięta",
  unexpectedRollback: "Nieoczekiwane cofnięcie operacji",
  transaction: "Błąd transakcji",
  roleRequestAlreadyExists: "Wniosek o rolę już istnieje",
  userAlreadyHasRole: "Użytkownik już ma tę rolę",
  applicationExists: "Aplikacja do tego lokalu już istnieje",
} satisfies {
  [key in ExceptionCode]: string;
};

const notFoundPage = {
  title: "Nie znaleziono strony",
  description: "Strona, której szukasz, nie istnieje",
};

const ownerLocals = {
  show: "Wyświetl",
  noLocalsFound: "Aktualnie nie posiadasz żadnych nieruchomości",
  addFirstLocal: "Dodaj pierwszą nieruchomość",
  title: "Strona główna",
  locals: "Nieruchomości",
  localState: "Stan lokalu",
  all: "Wszystkie",
  noLocalsFoundForThisState:
    "Aktualnie nie posiadasz żadnych lokali w tym stanie",
};

const allLocals = {
  title: "Wszystkie nieruchomości",
  show: "Wyświetl",
  localOwner: "Właściciel: ",
  noLocalsFound: "Aktualnie nie ma żadnych nieruchomości",
  noOwner: "Brak właściciela",
  localState: "Stan lokalu",
  all: "Wszystkie",
  noLocalsFoundWithGivenParameters: "Brak lokali spełniających kryteria",
  login: "Login właściciela",
};

const localState = {
  UNAPPROVED: "Niezaakceptowany",
  ARCHIVED: "Zarchiwizowany",
  ACTIVE: "Aktywny",
  INACTIVE: "Nieaktywny",
  WITHOUT_OWNER: "Bez właściciela",
  RENTED: "Wynajęty",
} satisfies {
  [key in LocalState]: string;
};

const roleRequestDialog = {
  description:
    "Możesz złożyć wniosek o przyznanie roli właściciela. Po otrzymaniu wniosku przeanalizujemy Twoje konto, aby ocenić Twoją kwalifikację. Jeśli rola zostanie Ci przyznana, otrzymasz powiadomienie e-mail.",
  requestOwnerRole: "Złóż prośbę o role właściciela",
  requestOwnerRoleDescription:
    "Kliknij ten przycisk, aby złożyć wniosek o przyznanie roli właściciela.",
  alreadyPlacedRequest: "Już złożyłeś wniosek o rolę o godzinie: ",
  howDoesItWork: "Jak to działa?",
  requestRoleButton: "Złóż wniosek",
};

const leaveLocal = {
  successTitle: "Sukces",
  successDescription: "Opuściłeś lokal",
  errorTitle: "Błąd",
  buttonText: "Opuść lokal",
  dialogTitle: "Czy na pewno chcesz opuścić lokal?",
  dialogDescription:
    "Nie możesz cofnąć tej operacji. Aby odzyskać lokal będziesz musiał skontaktować się z administratorem",
};

const tenantRents = {
  startDate: "Data rozpoczęcia",
  endDate: "Data zakończenia",
  fixedFee: "Opłata stała",
  balance: "Bilans",
  localSize: "Powierzchnia",
  owner: "Właściciel",
  name: "Imię",
  email: "Email",
  login: "Login",
  noData: "Aktualnie nie wynajmujesz żadnych lokali",
};

const currentOwnerRents = {
  title: "Właściciel",
  rents: "Obence wynajmy",
  startDate: "Data rozpoczęcia",
  endDate: "Data zakończenia",
  balance: "Bilans",
  tenant: "Najemca",
  name: "Imię i nazwisko",
  email: "Email",
  noRentsFound: "Aktualnie nie wynajmujesz żadnych lokali",
  rentDetails: "Szczegóły wynajmu",
  archivalRents: "Archiwalne wynajmy",
};

const currentTenantRents = {
  startDate: "Data rozpoczęcia",
  endDate: "Data zakończenia",
  fixedFee: "Opłata stała",
  balance: "Bilans",
  localSize: "Powierzchnia",
  owner: "Właściciel",
  name: "Imię",
  email: "Email",
  login: "Login",
};

const localDetails = {
  name: "Nazwa",
  error: "Błąd podczas pobierania danych o nieruchomości",
  firstName: "Imię",
  lastName: "Nazwisko",
  size: "Powierzchnia",
  rentalFee: "Opłata za wynajem",
  marginFee: "Marża właściciela",
  nextRentalFee: "Przyszła opłata za wynajem",
  nextMarginFee: "Przyszła marża właściciela",
  price: "Cena",
  totalPrice: "Razem",
  owner: "Właściciel",
  login: "Login",
  email: "Email",
  address: "Adres",
  country: "Kraj",
  city: "Miasto",
  street: "Ulica",
  number: "Numer",
  zipCode: "Kod pocztowy",
  description: "Opis",
  showOwnerDetails: "Pokaż dane właściciela",
  localInformation: "Informacje o nieruchomości",
  ownerInformation: "Informacje o właścicielu",
  addressInformation: "Informacje o adresie",
  basicInformation: "Podstawowe informacje",
  updateData: "Zaktualizuj dane lokalu",
  changeAddress: "Zmień adres",
  archiveLocal: "Zarchiwizuj lokal",
  approveLocal: "Zaakceptuj lokal",
  archiveLocalDescription: "Czy na pewno chcesz zarchiwizować lokal?",
  close: "Zamknij",
  archiveError: "Błąd podczas archiwizacji",
  archiveSuccess: "Lokal został zarchiwizowany",
  state: "Stan",
};

const ownLocalDetails = {
  error: "Błąd podczas pobierania danych o nieruchomości",
  size: "Powierzchnia",
  rentalFee: "Opłata za wynajem",
  marginFee: "Marża właściciela",
  nextRentalFee: "Przyszła opłata za wynajem",
  nextMarginFee: "Przyszła marża właściciela",
  address: "Adres",
  country: "Kraj",
  city: "Miasto",
  street: "Ulica",
  number: "Numer",
  zipCode: "Kod pocztowy",
  description: "Opis",
  localInformation: "Informacje o nieruchomości",
  addressInformation: "Informacje o adresie",
  basicInformation: "Podstawowe informacje",
  updateData: "Zaktualizuj dane lokalu",
  changeAddress: "Zmień adres",
  changeFixedFee: "Zmień wartość opłaty stałej",
  changeFixedFeeDescription:
    "Zmiany opłat stałych będą obowiązywać od następnego okresu rozliczeniowego.",
  leaveLocal: "Opuść lokal",
  leaveLocalDescription:
    "Nie możesz cofnąć tej operacji. Aby odzyskać lokal będziesz musiał skontaktować się z administratorem. Ta operacja nie może być wokonana na wynajętym lokalu",
  showApplications: "Przeglądaj aplikacje",
};

const activeLocals = {
  error: "Błąd podczas pobierania danych aktywnych nieruchomości",
  size: "Powierzchnia",
  city: "Miasto",
  show: "Wyświetl szczegóły",
};

const localApplications = {
  errorTitle: "Błąd podczas pobierania aplikacji",
  showApplications: "Pokaż aplikacje",
  applicant: "Wnioskodawca",
  createdAt: "Data złożenia aplikacji",
  accept: "Zaakceptuj",
  reject: "Odrzuć",
  noApplications: "Brak aplikacji",
  email: "Email",
  rejectTitle: "Odrzuć aplikację",
  rejectDescription: "Czy na pewno chcesz odrzucić aplikację?",
  rejectSuccess: "Aplikacja została odrzucona",
  acceptTitle: "Potwierdzenie akceptacji wniosku",
  acceptDescription:
    "Zaraz zaakceptujesz ten wniosek. Po akceptacji zostanie utworzony najem z datą zakończenia. Upewnij się, że wszystkie szczegóły są poprawne przed kontynuacją.",
  acceptFooter: "Po zaakceptowaniu aplikacji lokal zostanie wynajęty",
  acceptSuccess: "Aplikacja została zaakceptowana",
  endDateNeeded: "Musisz wprowadzić datę",
};

const activeLocalDetails = {
  firstName: "Imię",
  size: "Powierzchnia",
  price: "Cena",
  owner: "Właściciel",
  city: "Miasto",
  description: "Opis",
  localInformation: "Informacje o nieruchomości",
  ownerInformation: "Informacje o właścicielu",
  apply: "Aplikuj",
  applicationTitle: "Aplikacja",
  applicationDescription: "Na pewno chcesz aplikować do lokalu?",
  applicationExistsDescription: "Aplikowałeś do lokalu: ",
  applicationCreated: "Utworzono aplikacje",
};

const updateLocalPage = {
  state: "Stan",
  changeState: "Zmień stan na",
  states: {
    withoutOwner: "Bez własciciela",
    unapproved: "Nie zatwierdzony",
    archived: "Zarchiwizowany",
    active: "Aktywny",
    inactive: "Nieaktywny",
    rented: "Wynajety",
    unknown: "Nieznany",
  },
  updateData: "Zaktualizuj dane lokalu",
  name: "Nazwa nieruchomości",
  description: "Opis nieruchomości",
  size: "Powierzchnia",
  reset: "Resetuj",
  submit: "Zgłoś",
  wrong: {
    name: "Nieprawidłowa nazwa",
    description: "Nieprawidłowy opis",
    size: "Nieprawidłowa powierzchnia",
    state: "Nieprawidłowy stan",
  },
  successTitle: "Sukces",
  successDescription: "Nieruchomość zaktualizowana",
  errorTitle: "Błąd",
  confirmDialogDescription: "Czy na pewno chcesz zmienić dane lokalu?",
};

const changeEndDate = {
  successTitle: "Sukces",
  successDescription: "Data zakończenia została zmieniona",
  errorTitle: "Błąd",
  buttonText: "Zmień datę zakończenia",
  newDateRequired: "Nowa data jest wymagana",
  dialogTitle: "Zmień datę zakończenia wynajmu",
  dialogDescription: "Wybierz nową datę zakończenia wynajmu",
  formLabel: "Nowa data zakończenia",
  formDescription:
    "Data zakończenie wynajmu musi być w przyszłości i musi być Niedzielą",
  spanText: "Wybierz datę",
  saveChanges: "Zapisz zmiany",
};

const changeAddressForm = {
  cityValidation: "Miasto musi zawierać od 1 do 100 znaków",
  countryValidation: "Kraj musi zawierać od 1 do 100 znaków",
  streetValidation: "Ulica musi zawierać od 1 do 100 znaków",
  numberValidation: "Numer musi zawierać od 1 do 10 znaków",
  zipCodeValidation: "Kod pocztowy musi być w formacie 12-345",
  country: "Kraj*",
  city: "Miasto*",
  street: "Ulica*",
  number: "Numer*",
  zipCode: "Kod pocztowy*",
  confirmDialogDescription: "Czy na pewno chcesz zmienić adres?",
  addressUpdateSuccess: "Adres został zaktualizowany",
};

const updateOwnLocalFixedFeeForm = {
  rentalFeeNotEmpty: "Opłata za wynajem nie może być pusta.",
  rentalFeeNotValid:
    "Opłata za wynajem musi być prawidłową kwotą pieniężną z maksymalnie 2 miejscami po przecinku.",
  marginFeeNotEmpty: "Marża nie może być pusta.",
  marginFeeNotValid:
    "Marża musi być prawidłową kwotą pieniężną z maksymalnie 2 miejscami po przecinku.",
  rentalFeeInput: "Wprowadź opłatę za wynajem",
  marginFeeInput: "Wprowadź marżę",
  updateFixedFee: "Zaktualizuj stałą opłatę",
  updateFixedFeeTitle: "Potwierdź aktualizację",
  updateFixedFeeDescription: "Czy na pewno chcesz zaktualizować stałą opłatę?",
  rentalFeeTooLarge: "Opłata za wynajem nie może przekroczyć 10 000.",
  marginFeeTooLarge: "Marża nie może przekroczyć 10 000.",
};

const changeFixedFee = {
  successTitle: "Sukces",
  successDescription: "Opłata stała została zmieniona",
  errorTitle: "Błąd",
};

const ownerRentDetails = {
  error: "Błąd podczas pobierania danych o wynajmie",
  ownerMainPage: "Właściciel",
  rents: "Obecne wynajmy",
  rent: "Wynajem lokalu ",
  rentDetails: "Szczegóły wynajmu",
  rentInfo: "Informacje o wynajmie",
  tenantInfo: "Informacje o najemcy",
  addressInfo: "Informacje o adresie",
  payments: "Płatności",
  fixedFees: "Opłaty stałe",
  noPayments: "Brak płatności",
  variableFees: "Opłaty zmienne",
  localInfo: "Informacje o lokalu",
  startDate: "Data rozpoczęcia",
  endDate: "Data zakończenia",
  name: "Imię",
  email: "Email",
  login: "Login",
  address: "Adres",
  date: "Data",
  amount: "Kwota",
  selectStart: "Wybierz datę początkową",
  selectEnd: "Wybierz datę końcową",
  number: "Nr",
  localName: "Nazwa",
  balance: "Bilans",
  pickDate: "Wybierz datę",
  margin: "Marża",
  rental: "Opłata za wynajem",
  summary: "Razem",
  showLocalDetails: "Pokaż szczegóły lokalu",
  archivalRents: "Archiwalne wynajmy",
};

const breadcrumbs = {
  tenant: "Najemca",
  locals: "Lokale",
  local: "Lokal",
  currentRents: "Aktualne wynajmy",
  archivalRents: "Archiwalne wynajmy",
  report: "Raport",
};

const tenantApplications = {
  createdAt: "Data utworzenia aplikacji o wynajem",
  linkToLocal: "Przejdź do lokalu",
  applicationsNotFund: "Brak aplikacji o wynajem",
  deleteApplication: "Usuń aplikację",
  deleteApplicationDescription: "Czy na pewno chcesz usunąć aplikację?",
  aplicationDeleted: "Usunięto aplikację",
};

const createVariableFeeDialog = {
  title: "Dodaj opłatę zmienną",
  amount: "Kwota*",
  amountMustBePositive: "Kwota musi być większa od 0",
  success: "Opłata zmienna została dodana",
};

const rentDetailsPage = {
  rentDetails: "Szczegóły wynajmu",
  localDetails: "Szczegóły lokalu",
  ownerDetails: "Informacje o właściciela",
  labels: {
    startDate: "Data rozpoczęcia",
    endDate: "Data zakończenia",
    balance: "Bilans",
    localName: "Nazwa",
    address: "Adres",
    fixedFee: "Opłata stała",
    name: "Imię",
    email: "Email",
  },
  details: "Szczegóły",
  payments: "Płatności",
  fixedFees: "Opłaty stałe",
  variableFees: "Opłaty zmienne",
};

const createPaymentDialog = {
  success: "Płatność została dodana",
  title: "Dodaj płatność",
  amount: "Kwota*",
  amountMustBePositive: "Kwota musi być większa od 0",
  amountMustBeLessThanOrEqualToTenThousand:
    "Kwota musi być mniejsza lub równa 10 000",
  amountMustHaveAtMostTwoFractionalDigits:
    "Kwota musi mieć maksymalnie dwie cyfry po przecinku",
};

const localReport = {
  marginFee: "Marża",
  rentalFee: "Opłata za wynajem",
  fixedFee: "Opłata stałe",
  variableFee: "Opłata zmienna",
  payment: "Płatność",
  summary: "Razem",
  totalPayments: "Suma płatności",
  totalFixedFees: "Suma opłat stałych",
  totalVariableFees: "Suma opłat zmiennych",
  totalMarginFees: "Zysk z marży",
  totalRentalFees: "Koszty wynajmu",
  localSummary: "Podsumowanie",
  longestRentDays: "Najdłuższy wynajem w dniach",
  shortestRentDays: "Najkrótszy wynajem w dniach",
  rentCount: "Liczba wynajmów",
  summaryPieChart: "Podsumowanie wpłat/opłat",
  summaryBarChart: "Wpłaty i opłaty podzielone na tygodnie",
};

export default {
  localReport,
  rentDetailsPage,
  createPaymentDialog,
  createVariableFeeDialog,
  changeAddressForm,
  tenantApplications,
  activeLocalDetails,
  ownerRentDetails,
  breadcrumbs,
  localDetails,
  updateOwnLocalFixedFeeForm,
  ownLocalDetails,
  changeFixedFee,
  roleRequestDialog,
  changeEndDate,
  leaveLocal,
  tenantRents,
  currentOwnerRents,
  currentTenantRents,
  allLocals,
  ownerLocals,
  updateLocalPage,
  activeLocals,
  localState,
  notFoundPage,
  sessionExpiredDialog,
  error,
  errors,
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
  block,
  userListPage,
  userFilter,
  userDetailsPage,
  updateDataForm,
  mePage,
  addLocalPage,
  userDataPage,
  localApplications,
  updateEmailPage,
  validation,
  pageChanger,
  notApprovedActionsPage,
  light: "Jasny",
  dark: "Ciemny",
  reactivationSuccess: "Konto zostało aktywowane, możesz się zalogować",
  success: "Operacja powiodła się",
  sessionExpired: "Sesja wygasła",
  sessionExpiredDescription: "Twoja sesja wygasła, zaloguj się ponownie",
  footer: "Landlord Kingdom - SSBD202402",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Potwierdź",
  cancel: "Anuluj",
  currency: " zł",
};
