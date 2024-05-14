import { Button } from "@/components/ui/button";
import {
  DropdownMenu,
  DropdownMenuCheckboxItem,
  DropdownMenuContent,
  DropdownMenuRadioGroup,
  DropdownMenuRadioItem,
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
import { useUsersFilterStore } from "@/store/usersFilterStore";
import { SearchCriteria } from "@/types/filter/SearchCriteria";
import { ChevronsUpDown } from "lucide-react";
import { FC, useState } from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";

interface FilterUsers {
  verified: boolean | null;
  blocked: boolean | null;
  login: string;
  email: string;
  roles: string[];
}

const UserFilter: FC = () => {
  const { t } = useTranslation();
  const allRoles = ["TENANT", "OWNER", "ADMINISTRATOR"];
  const [openRolesPanel, setOpenRolesPanel] = useState<boolean>(false);
  const [verifiedSelect, setVerifiedSelect] = useState<string>("both");
  const [blockedSelect, setBlockedSelect] = useState<string>("both");
  const store = useUsersFilterStore();

  const filterForm = useForm<FilterUsers>({
    values: {
      verified: null,
      blocked: null,
      login: "",
      email: "",
      roles: allRoles,
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

    store.setSearchCriteriaList({
      dataOption: "all",
      searchCriteriaList: criterias,
      roles: values.roles,
    });
  });

  const handleRoleCheck = (role: string) => {
    const current = filterForm.getValues("roles");
    if (current.includes(role)) {
      filterForm.setValue(
        "roles",
        current.filter((val) => val != role)
      );
    } else {
      current.push(role);
      filterForm.setValue("roles", current);
    }
  };

  return (
    <>
      <Form {...filterForm}>
        <form onSubmit={handleFilterSubmit}>
          <div className="flex gap-3">
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
                        className="flex gap-1"
                      >
                        {filterForm.getValues("verified") == null
                          ? t("userFilter.both")
                          : filterForm.getValues("verified") == true
                          ? t("common.yes")
                          : t("common.no")}
                        <ChevronsUpDown />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      <DropdownMenuRadioGroup
                        value={verifiedSelect}
                        onValueChange={setVerifiedSelect}
                      >
                        <DropdownMenuRadioItem
                          value="both"
                          onClick={() => filterForm.setValue("verified", null)}
                        >
                          {t("userFilter.both")}
                        </DropdownMenuRadioItem>
                        <DropdownMenuRadioItem
                          value="yes"
                          onClick={() => filterForm.setValue("verified", true)}
                        >
                          {t("userFilter.yes")}
                        </DropdownMenuRadioItem>
                        <DropdownMenuRadioItem
                          value="no"
                          onClick={() => filterForm.setValue("verified", false)}
                        >
                          {t("userFilter.no")}
                        </DropdownMenuRadioItem>
                      </DropdownMenuRadioGroup>
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
                        className="flex gap-1"
                      >
                        {filterForm.getValues("blocked") == null
                          ? t("userFilter.both")
                          : filterForm.getValues("blocked") == true
                          ? t("common.yes")
                          : t("common.no")}
                        <ChevronsUpDown />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      <DropdownMenuRadioGroup
                        value={blockedSelect}
                        onValueChange={setBlockedSelect}
                      >
                        <DropdownMenuRadioItem
                          value="both"
                          onClick={() => filterForm.setValue("blocked", null)}
                        >
                          {t("userFilter.both")}
                        </DropdownMenuRadioItem>
                        <DropdownMenuRadioItem
                          value="yes"
                          onClick={() => filterForm.setValue("blocked", true)}
                        >
                          {t("userFilter.yes")}
                        </DropdownMenuRadioItem>
                        <DropdownMenuRadioItem
                          value="no"
                          onClick={() => filterForm.setValue("blocked", false)}
                        >
                          {t("userFilter.no")}
                        </DropdownMenuRadioItem>
                      </DropdownMenuRadioGroup>
                    </DropdownMenuContent>
                  </DropdownMenu>
                </FormItem>
              )}
            />
            <FormField
              control={filterForm.control}
              name="login"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>{t("userFilter.login")}</FormLabel>
                  <FormControl>
                    <Input {...field} type="text" placeholder=". . ." />
                  </FormControl>
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
            <FormField
              control={filterForm.control}
              name="roles"
              render={() => (
                <FormItem>
                  <FormLabel>{t("userFilter.roles")}</FormLabel>
                  <DropdownMenu
                    open={openRolesPanel}
                    onOpenChange={setOpenRolesPanel}
                  >
                    <DropdownMenuTrigger asChild>
                      <Button
                        variant={"outline"}
                        role="combobox"
                        className="flex gap-1 w-96 justify-between"
                      >
                        <div className="flex gap-1">
                          {filterForm.getValues("roles").map((role) => (
                            <div
                              key={role}
                              className="border-2 rounded-3xl p-1"
                            >
                              {t(`common.${role.toLowerCase()}`)}
                            </div>
                          ))}
                        </div>
                        <ChevronsUpDown />
                      </Button>
                    </DropdownMenuTrigger>
                    <DropdownMenuContent>
                      {allRoles.map((role) => (
                        <DropdownMenuCheckboxItem
                          key={role}
                          checked={filterForm.getValues("roles").includes(role)}
                          onCheckedChange={() => handleRoleCheck(role)}
                          onSelect={(e) => e.preventDefault()}
                        >
                          {t(`common.${role.toLowerCase()}`)}
                        </DropdownMenuCheckboxItem>
                      ))}
                    </DropdownMenuContent>
                  </DropdownMenu>
                </FormItem>
              )}
            />
            <div className="flex">
              <Button type="submit">{t("userFilter.submit")}</Button>
            </div>
          </div>
        </form>
      </Form>
    </>
  );
};

export default UserFilter;
