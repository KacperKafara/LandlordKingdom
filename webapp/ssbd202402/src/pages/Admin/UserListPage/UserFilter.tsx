import RefreshQueryButton from "@/components/RefreshQueryButton";
import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
} from "@/components/ui/form";

import { Input } from "@/components/ui/input";
import { Role } from "@/store/userStore";
import { useUsersFilterStore } from "@/store/usersFilterStore";
import { SearchCriteria } from "@/types/filter/SearchCriteria";
import { ChevronsUpDown } from "lucide-react";
import { LuFilterX } from "react-icons/lu";
import { FC, useState } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { useAutocompletionQuery } from "@/data/useAutocompletion";
import LoginInput from "./LoginInput";

interface FilterUsers {
  verified: boolean | null;
  blocked: boolean | null;
  login: string;
  email: string;
  lastName: string;
  firstName: string;
  role: string;
}

const UserFilter: FC = () => {
  const { t } = useTranslation();
  const allRoles = ["ALL", "TENANT", "OWNER", "ADMINISTRATOR"];
  const store = useUsersFilterStore();
  const [debouncedLoginPattern, setDebouncedLoginPattern] =
    useState<string>("");
  const { data: autocompleteData } = useAutocompletionQuery(
    debouncedLoginPattern
  );

  const filterForm = useForm<FilterUsers>({
    values: {
      verified: null,
      blocked: null,
      login: "",
      email: "",
      lastName: "",
      firstName: "",
      role: "ALL",
    },
  });

  const handleFilterSubmit = filterForm.handleSubmit(async (values) => {
    const criterias: SearchCriteria[] = [];

    if (values.blocked != null) {
      criterias.push({
        filterKey: "blocked",
        operation: "eq",
        value: values.blocked,
      });
    }

    if (values.verified != null) {
      criterias.push({
        filterKey: "verified",
        operation: "eq",
        value: values.verified,
      });
    }

    if (values.login != "") {
      criterias.push({
        filterKey: "login",
        operation: "cn",
        value: values.login,
      });
    }

    if (values.email != "") {
      criterias.push({
        filterKey: "email",
        operation: "cn",
        value: values.email,
      });
    }

    if (values.lastName != "") {
      criterias.push({
        filterKey: "lastName",
        operation: "cn",
        value: values.lastName,
      });
    }

    if (values.firstName != "") {
      criterias.push({
        filterKey: "firstName",
        operation: "cn",
        value: values.firstName,
      });
    }

    store.setSearchCriteriaList({
      dataOption: "all",
      searchCriteriaList: criterias,
      role: values.role,
    });
    store.setPageNumber(0);
  });

  const handleClearFilters = () => {
    filterForm.reset();
    store.setSearchCriteriaList({
      dataOption: "all",
      searchCriteriaList: [],
      role: "ALL",
    });
    store.setPageNumber(0);
  };

  return (
    <>
      <Form {...filterForm}>
        <form onSubmit={handleFilterSubmit}>
          <div className="flex flex-wrap justify-center gap-3">
            <FormField
              control={filterForm.control}
              name="verified"
              render={() => (
                <FormItem>
                  <FormLabel>{t("userFilter.verified")}</FormLabel>
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button
                        variant={"outline"}
                        role="combobox"
                        className="flex w-24 justify-between"
                      >
                        {filterForm.getValues("verified") == null
                          ? t("userFilter.both")
                          : filterForm.getValues("verified") == true
                            ? t("common.yes")
                            : t("common.no")}
                        <ChevronsUpDown className="size-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("verified") === null}
                        onCheckedChange={() =>
                          filterForm.setValue("verified", null)
                        }
                      >
                        {t("userFilter.both")}
                      </DropdownMenuCheckboxItem>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("verified") === true}
                        onCheckedChange={() =>
                          filterForm.setValue("verified", true)
                        }
                      >
                        {t("userFilter.yes")}
                      </DropdownMenuCheckboxItem>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("verified") === false}
                        onCheckedChange={() =>
                          filterForm.setValue("verified", false)
                        }
                      >
                        {t("userFilter.no")}
                      </DropdownMenuCheckboxItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="blocked"
              render={() => (
                <FormItem>
                  <FormLabel>{t("userFilter.blocked")}</FormLabel>
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button
                        variant={"outline"}
                        role="combobox"
                        className="flex w-24 justify-between"
                      >
                        {filterForm.getValues("blocked") == null
                          ? t("userFilter.both")
                          : filterForm.getValues("blocked") == true
                            ? t("common.yes")
                            : t("common.no")}
                        <ChevronsUpDown className="size-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("blocked") === null}
                        onCheckedChange={() =>
                          filterForm.setValue("blocked", null)
                        }
                      >
                        {t("userFilter.both")}
                      </DropdownMenuCheckboxItem>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("blocked") === true}
                        onCheckedChange={() =>
                          filterForm.setValue("blocked", true)
                        }
                      >
                        {t("userFilter.yes")}
                      </DropdownMenuCheckboxItem>
                      <DropdownMenuCheckboxItem
                        checked={filterForm.getValues("blocked") === false}
                        onCheckedChange={() =>
                          filterForm.setValue("blocked", false)
                        }
                      >
                        {t("userFilter.no")}
                      </DropdownMenuCheckboxItem>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="role"
              render={() => (
                <FormItem>
                  <FormLabel>{t("userFilter.role")}</FormLabel>
                  <DropdownMenu>
                    <DropdownMenuTrigger asChild>
                      <Button
                        variant={"outline"}
                        role="combobox"
                        className="flex w-36 justify-between gap-1"
                      >
                        <div className="flex gap-1">
                          {t(
                            `userFilter.${
                              filterForm.getValues("role").toLowerCase() as Role
                            }`
                          )}
                        </div>
                        <ChevronsUpDown className="size-4" />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      {allRoles.map((role) => (
                        <DropdownMenuCheckboxItem
                          key={role}
                          checked={filterForm.getValues("role") === role}
                          onCheckedChange={() =>
                            filterForm.setValue("role", role)
                          }
                        >
                          {t(`userFilter.${role.toLowerCase() as Role}`)}
                        </DropdownMenuCheckboxItem>
                      ))}
                    </DropdownMenuContent>
                  </DropdownMenu>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="firstName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("userFilter.firstName")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="text" placeholder=". . ." />
                  </FormControl>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="lastName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("userFilter.lastName")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="text" placeholder=". . ." />
                  </FormControl>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="login"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("userFilter.login")}</FormLabel>
                  <LoginInput
                    {...field}
                    onLoginChange={(login) => {
                      field.onChange(login); // Update the field value
                      setDebouncedLoginPattern(login);
                    }}
                    autocompleteData={autocompleteData}
                  />
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("userFilter.email")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="text" placeholder=". . ." />
                  </FormControl>
                </FormItem>
              )}
            />
            <div className="flex gap-3">
              <div className="flex self-end">
                <Button type="submit">{t("userFilter.submit")}</Button>
              </div>
              <div className="flex self-end">
                <Button
                  type="button"
                  variant="ghost"
                  className="flex items-center gap-2"
                  onClick={handleClearFilters}
                >
                  <LuFilterX size={20} />
                  {t("userFilter.clear")}
                </Button>
              </div>
              <div className="flex self-end">
                <RefreshQueryButton queryKeys={["filteredUsers"]} />
              </div>
            </div>
          </div>
        </form>
      </Form>
    </>
  );
};

export default UserFilter;
