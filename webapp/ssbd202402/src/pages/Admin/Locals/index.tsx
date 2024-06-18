import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { useGetAllLocals } from "@/data/mol/useGetAllLocals";
import { t } from "i18next";
import { FC, useState } from "react";
import { useBreadcrumbs } from "@/hooks/useBreadcrumbs";
import RefreshQueryButton from "@/components/RefreshQueryButton";
import { useNavigate } from "react-router-dom";
import { LoadingData } from "@/components/LoadingData";
import { PageChangerComponent } from "../../Components/PageChangerComponent";
import { getAddressString } from "@/utils/address";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { RiExpandUpDownLine } from "react-icons/ri";
import { Input } from "@/components/ui/input";
import { useDebounce } from "use-debounce";

interface LocalStateDropdown {
  shownValue: string;
  sendedValue: string;
}

const AllLocals: FC = () => {
  const [pageNumber, setPageNumber] = useState(0);
  const [pageSize, setPageSize] = useState(6);
  const [localState, setLocalState] = useState<LocalStateDropdown>({
    shownValue: t("allLocals.all"),
    sendedValue: "ALL",
  });
  const [ownerLogin, setOwnerLogin] = useState("");
  const [ownerLoginDebounced] = useDebounce(ownerLogin, 500);

  const { data: localsPage, isLoading } = useGetAllLocals({
    pageNumber: pageNumber,
    pageSize: pageSize,
    state: localState.sendedValue,
    ownerLogin: ownerLoginDebounced,
  });
  const locals = localsPage?.locals;
  const breadCrumbs = useBreadcrumbs([
    { title: t("roles.administrator"), path: "/admin" },
    { title: t("allLocals.title"), path: "/admin/locals" },
  ]);
  const navigate = useNavigate();

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOwnerLogin(e.target.value);
  };

  if (isLoading) {
    return <LoadingData />;
  }

  if (!locals) {
    return (
      <div className="relative flex h-full flex-col pt-1">
        {breadCrumbs}
        <div className="flex w-full justify-center pt-3">
          <p className="text-xl">{t("allLocals.noLocalsFound")}</p>
        </div>
        <RefreshQueryButton
          className="absolute -right-8 top-0"
          queryKeys={["allLocals"]}
        />
      </div>
    );
  }

  return (
    <div className="relative mt-1 flex flex-col justify-center">
      <div className="flex flex-row items-center justify-between">
        {breadCrumbs}
        <RefreshQueryButton queryKeys={["allLocals"]} />
      </div>
      <div className="flex justify-center">
        <div className="flex h-full flex-col justify-center">
          <ul className="flex flex-wrap gap-2 py-4">
            {locals.length === 0 && (
              <div className="flex flex-col">
                <p className="text-2xl">
                  {t("allLocals.noLocalsFoundWithGivenParameters")} 🤷‍♀️
                </p>
              </div>
            )}
            {locals.map((local) => (
              <li key={local.id} className="w-full min-w-[35rem] flex-1">
                <Card className="relative">
                  <Button
                    onClick={() => navigate(`local/${local.id}`)}
                    className="absolute right-1 top-1"
                    variant="ghost"
                  >
                    {t("allLocals.show")}
                  </Button>
                  <CardHeader>
                    <CardTitle>{local.name}</CardTitle>
                    <CardDescription className="flex flex-col">
                      <div>{getAddressString(local.address)}</div>
                      <div>
                        {(local.ownerLogin && (
                          <>
                            {t("allLocals.localOwner") + " " + local.ownerLogin}
                          </>
                        )) || <>{t("allLocals.noOwner")}</>}
                      </div>
                    </CardDescription>
                  </CardHeader>
                  <CardContent className="flex-grow">
                    {local.description}
                  </CardContent>
                  <CardFooter>{t(`localState.${local.state}`)}</CardFooter>
                </Card>
              </li>
            ))}
          </ul>
          <PageChangerComponent
            totalPages={localsPage.totalPages}
            pageNumber={pageNumber}
            pageSize={pageSize}
            setPageNumber={setPageNumber}
            setNumberOfElements={setPageSize}
            className="mb-3 flex justify-between"
          >
            <div className="flex w-full items-center gap-8">
              <div className="flex items-center justify-center gap-2">
                <p>{t("allLocals.localState")}</p>
                <DropdownMenu>
                  <DropdownMenuTrigger>
                    <Button
                      className="flex h-8 items-center px-2"
                      variant="outline"
                      role="combobox"
                    >
                      {localState.shownValue}
                      <RiExpandUpDownLine className="ml-3 text-sm" />
                    </Button>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("allLocals.all"),
                          sendedValue: "ALL",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("allLocals.all")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.ACTIVE"),
                          sendedValue: "ACTIVE",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.ACTIVE")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.INACTIVE"),
                          sendedValue: "INACTIVE",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.INACTIVE")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.RENTED"),
                          sendedValue: "RENTED",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.RENTED")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.UNAPPROVED"),
                          sendedValue: "UNAPPROVED",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.UNAPPROVED")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.WITHOUT_OWNER"),
                          sendedValue: "WITHOUT_OWNER",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.WITHOUT_OWNER")}
                    </DropdownMenuItem>
                    <DropdownMenuItem
                      onSelect={() =>
                        setLocalState({
                          shownValue: t("localState.ARCHIVED"),
                          sendedValue: "ARCHIVED",
                        })
                      }
                      className="h-8 px-2"
                    >
                      {t("localState.ARCHIVED")}
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
              <div className="flex items-center justify-center gap-4">
                <p>{t("allLocals.login")}</p>
                <Input
                  type="text"
                  value={ownerLogin}
                  onChange={handleInputChange}
                  className="max-h-fit max-w-fit text-sm"
                />
              </div>
            </div>
          </PageChangerComponent>
        </div>
      </div>
    </div>
  );
};

export default AllLocals;
